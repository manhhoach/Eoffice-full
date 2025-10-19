package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.dto.processStatus.CreateProcessStatusReq;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusPagingReq;
import com.manhhoach.EofficeFull.service.ProcessStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process-statuses")
@RequiredArgsConstructor
public class ProcessStatusController {
    private final ProcessStatusService processStatusService;

    @IsAuthorized(PermissionConstant.VIEW_STATUSES)
    @GetMapping
    public ApiResponse<PagedResponse<ProcessStatusDto>> getPaged(ProcessStatusPagingReq request) {
        return ApiResponse.success(
                processStatusService.getPaged(request)
        );
    }

    @PostMapping
    public ApiResponse<ProcessStatusDto> create(@RequestBody CreateProcessStatusReq req) {
        return ApiResponse.success(processStatusService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProcessStatusDto> update(@PathVariable Long id, @RequestBody CreateProcessStatusReq req) {
        return ApiResponse.success(processStatusService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        processStatusService.delete(id);
        return ApiResponse.success(null);
    }
}
