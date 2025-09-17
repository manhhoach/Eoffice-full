package com.manhhoach.EofficeFull.controller;


import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.dto.auth.LoginReq;
import com.manhhoach.EofficeFull.dto.auth.RefreshTokenReq;
import com.manhhoach.EofficeFull.dto.auth.RegisterReq;
import com.manhhoach.EofficeFull.dto.auth.LoginRes;
import com.manhhoach.EofficeFull.dto.user.UserDto;
import com.manhhoach.EofficeFull.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${security.jwt.refresh-token.expiration}")
    private int refreshTokenExpirationMs;

    @PostMapping("/login")
    public ApiResponse<LoginRes> login(@RequestBody LoginReq req, HttpServletResponse response) {
        LoginRes res = authService.login(req);

        ResponseCookie cookie = ResponseCookie.from("myCookieName", "myCookieValue")
                .path("/") // Set the path for the cookie
                .sameSite("Lax") // Set SameSite to "Lax", "Strict", or "None"
                .httpOnly(true) // Optional: Prevents client-side script access
                .secure(false) // Optional: Ensures the cookie is only sent over HTTPS
                .maxAge(3600) // Optional: Sets the cookie's max age in seconds
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ApiResponse.success(res);
    }

    @PostMapping("/register")
    public ApiResponse<Boolean> register(@RequestBody RegisterReq req) {
        return ApiResponse.success(authService.register(req));
    }

    @IsAuthorized({})
    @GetMapping("/me")
    public ApiResponse<UserDto> getMe() {
        return ApiResponse.success(authService.getMe());
    }

    @PostMapping("/refresh-token")
    public ApiResponse<LoginRes> refreshToken(@RequestBody RefreshTokenReq refreshTokenReq) {
        return ApiResponse.success(authService.refreshToken(refreshTokenReq));
    }
}
