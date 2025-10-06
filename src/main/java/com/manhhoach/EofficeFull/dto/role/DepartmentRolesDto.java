package com.manhhoach.EofficeFull.dto.role;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DepartmentRolesDto {
    private Long departmentId;
    private String departmentName;
    private List<UserRoleStatusDto> userRoleStatuses;
}
