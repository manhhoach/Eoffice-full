package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.permission.CreatePermissionReq;
import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.dto.permission.PermissionPagingReq;
import com.manhhoach.EofficeFull.dto.role.CreateRoleReq;
import com.manhhoach.EofficeFull.dto.role.RoleDto;

public interface PermissionService {
    PermissionDto create(CreatePermissionReq req);
    PermissionDto update(Long id, CreatePermissionReq req);
    void delete(Long id);
    PermissionDto getById(Long id);
    PagedResponse<PermissionDto> getPaged(PermissionPagingReq request);
}
