package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.role.*;

import java.util.List;

public interface RoleService {
    RoleDto create(CreateRoleReq req);

    RoleDto update(Long id, CreateRoleReq req);

    void delete(Long id);

    RoleDto getById(Long id);

    PagedResponse<RoleDto> getPaged(RolePagingReq request);

    List<DepartmentRolesDto> getCurrentRoles(Long userId);

    void assignRoles(Long userId, AssignUserRolesReq req);
}
