package com.manhhoach.EofficeFull.dto.module;

import lombok.Data;

import java.util.List;

@Data
public class SelectedModuleReq {
    private List<Long> permissionIds;
}
