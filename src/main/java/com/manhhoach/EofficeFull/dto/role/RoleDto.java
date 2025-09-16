package com.manhhoach.EofficeFull.dto.role;

import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class RoleDto {
    private String code;
    private String name;
    private List<PermissionDto> permissions;
}
