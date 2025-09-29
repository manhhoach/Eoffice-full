package com.manhhoach.EofficeFull.dto.permission;


import com.manhhoach.EofficeFull.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private Long id;
    private String name;
    private String code;
    private String url;
    private Boolean isDisplayed;
    private Integer priority;

    public static PermissionDto map(Permission permission) {
        return PermissionDto.builder()
                .code(permission.getCode())
                .name(permission.getName())
                .id(permission.getId())
                .isDisplayed(permission.getIsDisplayed())
                .priority(permission.getPriority())
                .url(permission.getUrl())
                .build();
    }
}
