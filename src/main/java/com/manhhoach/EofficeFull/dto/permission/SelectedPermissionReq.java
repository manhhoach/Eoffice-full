package com.manhhoach.EofficeFull.dto.permission;

import lombok.Data;

import java.util.List;

@Data
public class SelectedPermissionReq {
    private Long userId;
    private List<Long> permissionIds;
}
