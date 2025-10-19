package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.dto.module.ModuleSelectionDto;
import com.manhhoach.EofficeFull.dto.module.SelectedModuleReq;
import com.manhhoach.EofficeFull.dto.role.*;
import com.manhhoach.EofficeFull.service.ModuleService;
import com.manhhoach.EofficeFull.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final ModuleService moduleService;

    @IsAuthorized(PermissionConstant.VIEW_ROLES)
    @GetMapping
    public ApiResponse<PagedResponse<RoleDto>> getPaged(RolePagingReq request) {
        return ApiResponse.success(
                roleService.getPaged(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleDto> getById(@PathVariable Long id) {
        return ApiResponse.success(roleService.getById(id));
    }

    @PostMapping
    public ApiResponse<RoleDto> create(@RequestBody CreateRoleReq req) {
        return ApiResponse.success(roleService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<RoleDto> update(@PathVariable Long id, @RequestBody CreateRoleReq req) {
        return ApiResponse.success(roleService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/{id}/modules")
    public ApiResponse<List<ModuleSelectionDto>> getSelectedModules(@PathVariable Long id) {
        return ApiResponse.success(moduleService.getSelectedModules(id));
    }

    @PutMapping("/{id}/modules")
    public ApiResponse<Void> setSelectedModules(@PathVariable Long id, @RequestBody SelectedModuleReq req) {
        moduleService.setSelectedModules(id, req);
        return ApiResponse.success(null);
    }

    @GetMapping("/users/{userId}")
    public ApiResponse<List<DepartmentRolesDto>> getCurrentRoles(@PathVariable Long userId) {
        return ApiResponse.success(roleService.getCurrentRoles(userId));
    }

    @PostMapping("/users/{userId}")
    public ApiResponse<Void> assignRoles(@PathVariable Long userId, @RequestBody AssignUserRolesReq req) {
        roleService.assignRoles(userId, req);
        return ApiResponse.success(null);
    }

}
