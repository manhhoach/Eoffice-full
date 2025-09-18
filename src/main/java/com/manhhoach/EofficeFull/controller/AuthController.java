package com.manhhoach.EofficeFull.controller;


import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.dto.auth.LoginReq;
import com.manhhoach.EofficeFull.dto.auth.RefreshTokenReq;
import com.manhhoach.EofficeFull.dto.auth.RegisterReq;
import com.manhhoach.EofficeFull.dto.auth.LoginRes;
import com.manhhoach.EofficeFull.dto.user.UserDto;
import com.manhhoach.EofficeFull.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ApiResponse<LoginRes> login(@RequestBody LoginReq req, HttpServletResponse response) {
        LoginRes res = authService.login(req);
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
