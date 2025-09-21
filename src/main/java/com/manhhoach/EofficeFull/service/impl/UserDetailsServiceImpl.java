package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.config.CustomUserDetails;
import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.repository.PermissionRepository;
import com.manhhoach.EofficeFull.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        List<Permission> permissions = permissionRepository.getPermissionsByUserId(user.getId());
        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(e -> new SimpleGrantedAuthority(e.getCode())).collect(Collectors.toList());
        return new CustomUserDetails(user.getId(), username, user.getPassword(), grantedAuthorities);
    }
}
