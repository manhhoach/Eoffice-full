package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.dto.auth.LoginReq;
import com.manhhoach.EofficeFull.dto.auth.RefreshTokenReq;
import com.manhhoach.EofficeFull.dto.auth.RegisterReq;
import com.manhhoach.EofficeFull.dto.auth.LoginRes;
import com.manhhoach.EofficeFull.dto.user.UserDto;

public interface AuthService {

    LoginRes login(LoginReq req);

    boolean register(RegisterReq req);

    UserDto getMe();

    LoginRes refreshToken(RefreshTokenReq req);
}
