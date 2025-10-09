package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
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

//    @GetMapping("/paged")
//    public ApiResponse<PagedResponse<ProcessStepDto>> getPaged(ProcessStepPagingReq request) {
//        return ApiResponse.success(
//                processStepService.getPaged(request)
//        );
//    }
//
//    @GetMapping("/{id}")
//    public ApiResponse<ProcessStepDto> getById(@PathVariable Long id) {
//        return ApiResponse.success(processStepService.getById(id));
//    }
//
//    @PostMapping
//    public ApiResponse<ProcessStepDto> create(@RequestBody CreateProcessStepReq req) {
//        return ApiResponse.success(processStepService.create(req));
//    }
//
//    @PutMapping("/{id}")
//    public ApiResponse<ProcessStepDto> update(@PathVariable Long id, @RequestBody CreateProcessStepReq req) {
//        return ApiResponse.success(processStepService.update(id, req));
//    }
//
//    @DeleteMapping("/{id}")
//    public ApiResponse<Void> delete(@PathVariable Long id) {
//        processStepService.delete(id);
//        return ApiResponse.success(null);
//    }

}
