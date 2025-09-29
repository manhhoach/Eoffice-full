package com.manhhoach.EofficeFull.dto.module;

import com.manhhoach.EofficeFull.dto.permission.PermissionSelectionDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ModuleSelectionDto {
    private String name;
    private List<PermissionSelectionDto> permissionSelectionDtos;
}
