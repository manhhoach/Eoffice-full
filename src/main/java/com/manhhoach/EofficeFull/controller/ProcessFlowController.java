package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.processFlow.CreateProcessFlowReq;
import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowDto;
import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowPagingReq;
import com.manhhoach.EofficeFull.service.ProcessFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process-flows")
@RequiredArgsConstructor
public class ProcessFlowController {
    private final ProcessFlowService processFlowService;

    @GetMapping("/paged")
    public ApiResponse<PagedResponse<ProcessFlowDto>> getPaged(ProcessFlowPagingReq request) {
        return ApiResponse.success(
                processFlowService.getPaged(request)
        );
    }

    @PostMapping
    public ApiResponse<ProcessFlowDto> create(@RequestBody CreateProcessFlowReq req) {
        return ApiResponse.success(processFlowService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProcessFlowDto> update(@PathVariable Long id, @RequestBody CreateProcessFlowReq req) {
        return ApiResponse.success(processFlowService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        processFlowService.delete(id);
        return ApiResponse.success(null);
    }
}
