package com.manhhoach.EofficeFull.filter;

import com.manhhoach.EofficeFull.config.CustomUserDetails;
import com.manhhoach.EofficeFull.provider.JwtTokenProvider;
import com.manhhoach.EofficeFull.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Value("${security.jwt.access-token.key}")
    private String accessTokenKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer")) {
                String accessToken = header.substring(7);
                if (jwtTokenProvider.validateToken(accessToken, accessTokenKey) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    String username = jwtTokenProvider.getUsername(accessToken, accessTokenKey);
                    var permissions = jwtTokenProvider.getPermissions(accessToken, accessTokenKey);
                    Long id = jwtTokenProvider.getId(accessToken, accessTokenKey);
                    var grantedAuthorities = permissions.stream().map(e -> new SimpleGrantedAuthority(e)).toList();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(new CustomUserDetails(id, username), null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            authEntryPoint.commence(request, response, ex);
        }
    }
}