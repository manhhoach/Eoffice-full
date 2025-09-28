package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.common.PagingRequest;
import com.manhhoach.EofficeFull.dto.permission.CreatePermissionReq;
import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.dto.permission.PermissionPagingReq;
import com.manhhoach.EofficeFull.dto.role.CreateRoleReq;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.service.PermissionService;
import com.manhhoach.EofficeFull.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping("/paged")
    public ApiResponse<PagedResponse<PermissionDto>> getPaged(PermissionPagingReq request) {
        return ApiResponse.success(
                permissionService.getPaged(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<PermissionDto> getById(@PathVariable Long id) {
        return ApiResponse.success(permissionService.getById(id));
    }

    @PostMapping
    public ApiResponse<PermissionDto> create(@RequestBody CreatePermissionReq req) {
        return ApiResponse.success(permissionService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<PermissionDto> update(@PathVariable Long id, @RequestBody CreatePermissionReq req) {
        return ApiResponse.success(permissionService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return ApiResponse.success(null);
    }
}
