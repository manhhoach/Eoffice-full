package com.manhhoach.EofficeFull.dto.auth;

import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginRes {
    private String accessToken;
    private String refreshToken;
    private String username;
    private List<PermissionDto> permissions;
}
