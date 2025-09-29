package com.manhhoach.EofficeFull.dto.permission;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionSelectionDto {
    private String name;
    private Long id;
    private Boolean selected;
}
