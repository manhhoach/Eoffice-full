package com.manhhoach.EofficeFull.dto.role;

import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class RoleDto {
    private Long id;
    private String code;
    private String name;
    private List<PermissionDto> permissions;

    public static RoleDto map(Role role){
        return RoleDto.builder().code(role.getCode()).name(role.getName()).id(role.getId()).build();
    }
}
