package com.manhhoach.EofficeFull.config;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private Long id;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String username, String password, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public CustomUserDetails(Long id, String username) {
        this.id = id;
        this.username = username;
    }


    public static CustomUserDetails getCurrentUserDetails() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AuthenticationCredentialsNotFoundException("Not authenticated");
        }

        return (CustomUserDetails) auth.getPrincipal();
    }
}
