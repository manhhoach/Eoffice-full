package com.manhhoach.EofficeFull.dto.role;

import lombok.Data;

import java.util.List;

@Data
public class AssignUserRolesReq {
    private List<RoleDepartmentReq> assignments;
}
