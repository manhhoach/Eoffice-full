package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.common.PagingRequest;
import com.manhhoach.EofficeFull.dto.role.CreateRoleReq;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/paged")
    public ApiResponse<PagedResponse<RoleDto>> getPaged(PagingRequest request) {
        return ApiResponse.success(
                roleService.getPaged(request.getPage(), request.getSize())
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
}
