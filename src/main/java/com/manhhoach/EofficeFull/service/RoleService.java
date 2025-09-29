package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.role.CreateRoleReq;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.dto.role.RolePagingReq;

public interface RoleService {
    RoleDto create(CreateRoleReq req);

    RoleDto update(Long id, CreateRoleReq req);

    void delete(Long id);

    RoleDto getById(Long id);

    PagedResponse<RoleDto> getPaged(RolePagingReq request);
}
