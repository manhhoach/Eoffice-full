package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.dto.processFlow.CreateProcessFlowReq;
import com.manhhoach.EofficeFull.dto.processFlow.FlowDataDto;
import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowDto;
import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowPagingReq;
import com.manhhoach.EofficeFull.dto.processStep.StepConfig;
import com.manhhoach.EofficeFull.service.ProcessFlowService;
import com.manhhoach.EofficeFull.service.ProcessStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process-flows")
@RequiredArgsConstructor
public class ProcessFlowController {
    private final ProcessFlowService processFlowService;
    private final ProcessStepService processStepService;

    @IsAuthorized(PermissionConstant.VIEW_FLOWS)
    @GetMapping
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

    @GetMapping("/{id}/graph")
    public ApiResponse<FlowDataDto> getConfigFlow(@PathVariable Long id) {
        return ApiResponse.success(processFlowService.getFlowData(id));
    }


    @GetMapping("/{flowId}/steps/meta")
    public ApiResponse<StepConfig> getStepMeta(@PathVariable Long flowId) {
        return ApiResponse.success(processStepService.getConfigStep(flowId));
    }

}
