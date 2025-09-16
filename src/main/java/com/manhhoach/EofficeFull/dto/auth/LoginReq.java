package com.manhhoach.EofficeFull.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginReq {
    private String username;
    private String password;
}
