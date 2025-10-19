package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.dto.permission.*;
import com.manhhoach.EofficeFull.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @IsAuthorized(PermissionConstant.VIEW_PERMISSIONS)
    @GetMapping
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

    @GetMapping("/users/{userId}")
    public ApiResponse<List<PermissionSelectionDto>> getUserPermissions(@PathVariable Long userId) {
        return ApiResponse.success(permissionService.getSelectedPermissions(userId));
    }

    @PutMapping("/users/{userId}")
    public ApiResponse<Void> setUserPermissions(@PathVariable Long userId, @RequestBody SelectedPermissionReq req) {
        permissionService.setSelectedPermissions(userId, req);
        return ApiResponse.success(null);
    }
}
