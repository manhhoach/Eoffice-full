package com.manhhoach.EofficeFull.dto.res;

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
