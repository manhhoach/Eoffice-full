package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.dto.role.AssignUserRolesReq;
import com.manhhoach.EofficeFull.dto.role.DepartmentRolesDto;
import com.manhhoach.EofficeFull.dto.user.CreateUserReq;
import com.manhhoach.EofficeFull.dto.user.UserDto;
import com.manhhoach.EofficeFull.dto.user.UserPagingReq;
import com.manhhoach.EofficeFull.service.RoleService;
import com.manhhoach.EofficeFull.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @IsAuthorized(PermissionConstant.VIEW_USERS)
    @GetMapping
    public ApiResponse<PagedResponse<UserDto>> getPaged(UserPagingReq request) {
        return ApiResponse.success(
                userService.getPaged(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDto> getById(@PathVariable Long id) {
        return ApiResponse.success(userService.getById(id));
    }

    @PostMapping
    public ApiResponse<UserDto> create(@RequestBody CreateUserReq req) {
        return ApiResponse.success(userService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> update(@PathVariable Long id, @RequestBody CreateUserReq req) {
        return ApiResponse.success(userService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/{userId}/roles")
    public ApiResponse<List<DepartmentRolesDto>> getCurrentRoles(@PathVariable Long userId) {
        return ApiResponse.success(roleService.getCurrentRoles(userId));
    }

    @PutMapping("/{userId}/roles")
    public ApiResponse<Void> assignRoles(@PathVariable Long userId, @RequestBody AssignUserRolesReq req) {
        roleService.assignRoles(userId, req);
        return ApiResponse.success(null);
    }
}
