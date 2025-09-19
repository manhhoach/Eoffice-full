package com.manhhoach.EofficeFull.dto.auth;

import com.manhhoach.EofficeFull.dto.module.ModuleDto;
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
    private List<ModuleDto> modules;
    private List<String> permissionCodes;

    public List<String> getPermissionCodes() {
        if (modules == null) return List.of();
        return modules.stream()
                .flatMap(module -> module.getPermissions().stream())
                .map(PermissionDto::getCode)
                .distinct()
                .toList();
    }
}
