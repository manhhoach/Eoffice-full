package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.common.PagingRequest;
import com.manhhoach.EofficeFull.dto.module.CreateModuleReq;
import com.manhhoach.EofficeFull.dto.module.ModuleDto;
import com.manhhoach.EofficeFull.dto.module.ModulePagingReq;
import com.manhhoach.EofficeFull.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;

    @GetMapping("/paged")
    public ApiResponse<PagedResponse<ModuleDto>> getPaged(ModulePagingReq request) {
        return ApiResponse.success(
                moduleService.getPaged(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ModuleDto> getById(@PathVariable Long id) {
        return ApiResponse.success(moduleService.getById(id));
    }

    @PostMapping
    public ApiResponse<ModuleDto> create(@RequestBody CreateModuleReq req) {
        return ApiResponse.success(moduleService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<ModuleDto> update(@PathVariable Long id, @RequestBody CreateModuleReq req) {
        return ApiResponse.success(moduleService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        moduleService.delete(id);
        return ApiResponse.success(null);
    }
}
