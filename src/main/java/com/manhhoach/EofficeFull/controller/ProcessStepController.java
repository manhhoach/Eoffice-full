package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.dto.processStep.*;
import com.manhhoach.EofficeFull.service.ProcessStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process-steps")
@RequiredArgsConstructor
public class ProcessStepController {
    private final ProcessStepService processStepService;

    @IsAuthorized(PermissionConstant.VIEW_STEPS)
    @GetMapping("/paged")
    public ApiResponse<PagedResponse<ProcessStepDto>> getPaged(ProcessStepPagingReq request) {
        return ApiResponse.success(
                processStepService.getPaged(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ProcessStepDto> getById(@PathVariable Long id) {
        return ApiResponse.success(processStepService.getById(id));
    }

    @PostMapping
    public ApiResponse<Void> create(@RequestBody CreateProcessStepReq req) {
        processStepService.create(req);
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody CreateProcessStepReq req) {
        processStepService.update(id, req);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        processStepService.delete(id);
        return ApiResponse.success(null);
    }


    @GetMapping("/config/{flowId}")
    public ApiResponse<StepConfig> getConfigStep(@PathVariable Long flowId){
        return ApiResponse.success(processStepService.getConfigStep(flowId));
    }

}
