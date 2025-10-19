package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.permission.*;

import java.util.List;

public interface PermissionService {
    PermissionDto create(CreatePermissionReq req);

    PermissionDto update(Long id, CreatePermissionReq req);

    void delete(Long id);

    PermissionDto getById(Long id);

    PagedResponse<PermissionDto> getPaged(PermissionPagingReq request);

    List<PermissionSelectionDto> getSelectedPermissions(Long userId);

    void setSelectedPermissions(Long userId, SelectedPermissionReq req);
}
