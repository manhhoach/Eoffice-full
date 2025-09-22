package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.config.CustomUserDetails;
import com.manhhoach.EofficeFull.constant.RoleConstant;
import com.manhhoach.EofficeFull.dto.auth.LoginReq;
import com.manhhoach.EofficeFull.dto.auth.RefreshTokenReq;
import com.manhhoach.EofficeFull.dto.auth.RegisterReq;
import com.manhhoach.EofficeFull.dto.auth.LoginRes;
import com.manhhoach.EofficeFull.dto.module.ModuleDto;
import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.dto.user.UserDto;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.entity.User;
import com.manhhoach.EofficeFull.provider.JwtTokenProvider;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
import com.manhhoach.EofficeFull.repository.PermissionRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.repository.UserRepository;
import com.manhhoach.EofficeFull.service.AuthService;
import com.manhhoach.EofficeFull.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PermissionRepository permissionRepository;
    private final ModuleService moduleService;

    @Value("${security.jwt.access-token.key}")
    private String accessTokenKey;

    @Value("${security.jwt.access-token.expiration}")
    private long accessTokenExpirationMs;

    @Value("${security.jwt.refresh-token.key}")
    private String refreshTokenKey;

    @Value("${security.jwt.refresh-token.expiration}")
    private long refreshTokenExpirationMs;

    @Override
    public LoginRes login(LoginReq req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return buildLoginRes(userDetails.getId(), userDetails.getUsername());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid info");
        }

    }

    @Override
    public boolean register(RegisterReq req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        String hashedPassword = passwordEncoder.encode(req.getPassword());
        Role defaultRole = roleRepository.findByName(RoleConstant.USER)
                .orElseThrow(() -> new RuntimeException("Default role is not exists"));

        User user = User.builder()
                .username(req.getUsername())
                .password(hashedPassword)
                .roles(List.of(defaultRole))
                .build();

        userRepository.save(user);
        return true;
    }

    @Override
    public UserDto getMe() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        LoginRes loginRes = buildLoginRes(userDetails.getId(), userDetails.getUsername());
        return UserDto.builder()
                .permissionCodes(loginRes.getPermissionCodes())
                .modules(loginRes.getModules())
                .username(userDetails.getUsername())
                .build();
    }

    @Override
    public LoginRes refreshToken(RefreshTokenReq req) {
        String username = jwtTokenProvider.getUsername(req.getRefreshToken(), refreshTokenKey);
        var id = jwtTokenProvider.getId(req.getRefreshToken(), refreshTokenKey);
        return buildLoginRes(id, username);
    }

    private LoginRes buildLoginRes(Long id, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);

        List<Permission> permissions = permissionRepository.getPermissionsByUserId(id);
        List<String> permissionCodes = permissions.stream().map(per->per.getCode()).toList();

        LoginRes data = LoginRes.builder()
                .username(username)
                .modules(groupPermissions(permissions))
                .permissionCodes(permissionCodes)
                .build();

        claims.put("permissions", permissionCodes);

        String accessToken = jwtTokenProvider.generateToken(username, claims, accessTokenKey, accessTokenExpirationMs);
        String refreshToken = jwtTokenProvider.generateToken(username, claims, refreshTokenKey, refreshTokenExpirationMs);

        data.setAccessToken(accessToken);
        data.setRefreshToken(refreshToken);
        return data;
    }

    List<ModuleDto> groupPermissions(List<Permission> permissions){
        Map<Long, ModuleDto> moduleMap = new LinkedHashMap<>();

        for (Permission p : permissions) {
            Module module = p.getModule();
            if (module == null) {
                continue;
            }

            ModuleDto moduleDto = moduleMap.computeIfAbsent(module.getId(), k -> ModuleDto.map(module));

            if (moduleDto.getPermissions() == null) {
                moduleDto.setPermissions(new ArrayList<>());
            }
            moduleDto.getPermissions().add(PermissionDto.map(p));
        }

        List<ModuleDto> result = new ArrayList<>(moduleMap.values());
        return result;
    }

}
