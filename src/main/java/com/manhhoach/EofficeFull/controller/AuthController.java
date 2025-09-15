package com.manhhoach.EofficeFull.controller;


import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.dto.req.LoginReq;
import com.manhhoach.EofficeFull.dto.req.RefreshTokenReq;
import com.manhhoach.EofficeFull.dto.req.RegisterReq;
import com.manhhoach.EofficeFull.dto.res.LoginRes;
import com.manhhoach.EofficeFull.dto.res.UserDto;
import com.manhhoach.EofficeFull.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginRes> login(@RequestBody LoginReq req) {
        return ApiResponse.success(authService.login(req));
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
