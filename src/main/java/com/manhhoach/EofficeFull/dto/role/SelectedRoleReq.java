package com.manhhoach.EofficeFull.dto.role;

import lombok.Data;

import java.util.List;

@Data
public class SelectedRoleReq {
    private Long userId;
    private List<Long> roleIds;
}
