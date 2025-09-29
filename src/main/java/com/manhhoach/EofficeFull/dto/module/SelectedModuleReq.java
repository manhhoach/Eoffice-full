package com.manhhoach.EofficeFull.dto.module;

import lombok.Data;

import java.util.List;

@Data
public class SelectedModuleReq {
    private Long roleId;
    private List<Long> permissionIds;
}
