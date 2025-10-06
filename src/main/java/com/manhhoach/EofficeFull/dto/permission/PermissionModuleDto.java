package com.manhhoach.EofficeFull.dto.permission;

import com.manhhoach.EofficeFull.dto.module.ModuleDto;
import lombok.Data;


@Data
public class PermissionModuleDto {
    private Long id;
    private String name;
    private String code;
    private String url;
    private Boolean isDisplayed;
    private Integer priority;
    private ModuleDto module;


    public PermissionModuleDto(Long id, String name, String code, String url, Boolean isDisplayed, Integer priority,
                           Long moduleId, String moduleName, String moduleCode, Boolean moduleIsDisplayed    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.url = url;
        this.isDisplayed = isDisplayed;
        this.priority = priority;
        this.module = ModuleDto.builder()
                .code(moduleCode)
                .name(moduleName)
                .id(moduleId)
                .isDisplayed(moduleIsDisplayed)
                .build();;
    }
}
