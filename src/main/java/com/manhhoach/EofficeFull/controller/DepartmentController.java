package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.dto.department.CreateDepartmentReq;
import com.manhhoach.EofficeFull.dto.department.DepartmentDto;
import com.manhhoach.EofficeFull.dto.department.DepartmentPagingReq;
import com.manhhoach.EofficeFull.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @IsAuthorized(PermissionConstant.VIEW_DEPARTMENTS)
    @GetMapping("/paged")
    public ApiResponse<PagedResponse<DepartmentDto>> getPaged(DepartmentPagingReq request) {
        return ApiResponse.success(
                departmentService.getPaged(request)
        );
    }

    @PostMapping
    public ApiResponse<DepartmentDto> create(@RequestBody CreateDepartmentReq req) {
        return ApiResponse.success(departmentService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<DepartmentDto> update(@PathVariable Long id, @RequestBody CreateDepartmentReq req) {
        return ApiResponse.success(departmentService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ApiResponse.success(null);
    }
}
