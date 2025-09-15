package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.dto.req.LoginReq;
import com.manhhoach.EofficeFull.dto.req.RefreshTokenReq;
import com.manhhoach.EofficeFull.dto.req.RegisterReq;
import com.manhhoach.EofficeFull.dto.res.LoginRes;
import com.manhhoach.EofficeFull.dto.res.UserDto;

public interface AuthService {
    LoginRes login(LoginReq req);

    boolean register(RegisterReq req);

    UserDto getMe();

    LoginRes refreshToken(RefreshTokenReq req);
}
