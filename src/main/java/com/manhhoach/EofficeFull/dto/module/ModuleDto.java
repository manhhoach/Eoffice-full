package com.manhhoach.EofficeFull.dto.module;

import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.entity.Module;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDto {
    private Long id;
    private String name;
    private String code;
    private Boolean isDisplayed;

    public static ModuleDto map(Module module){
        return ModuleDto.builder()
                .code(module.getCode())
                .name(module.getName())
                .id(module.getId())
                .isDisplayed(module.getIsDisplayed())
                .build();
    }
}
