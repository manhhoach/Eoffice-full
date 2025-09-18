package com.manhhoach.EofficeFull.dto.permission;


import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private String name;
    private String code;
    private String url;
    private Boolean isDisplay;
    private Integer priority;
    private Long id;


    public static PermissionDto map(Permission permission){
        return PermissionDto.builder()
                .code(permission.getCode())
                .name(permission.getName())
                .id(permission.getId())
                .isDisplay(permission.getIsDisplay())
                .priority(permission.getPriority())
                .url(permission.getUrl())
                .build();
    }
}
