package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.dto.user.UserDto;
import com.manhhoach.EofficeFull.repository.UserRepository;
import com.manhhoach.EofficeFull.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getDetail(Long id) {
        var user = userRepository.getDetail(id).orElseThrow(() -> new RuntimeException());
        var roles = user.getRoles().stream().map(r -> {
            var permissions = r.getPermissions().stream().map(p ->
                    PermissionDto.builder().code(p.getCode()).build()
            ).toList();

            return RoleDto.builder().name(r.getName()).permissions(permissions).build();
        }).toList();


        var result = UserDto.builder()
                .username(user.getUsername())
                .roles(roles)
                .build();
        return result;
    }
}
