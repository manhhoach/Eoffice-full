package com.manhhoach.EofficeFull.dto.permission;

import lombok.Data;

import java.util.List;

@Data
public class SelectedPermissionReq {
    private List<Long> permissionIds;
}
