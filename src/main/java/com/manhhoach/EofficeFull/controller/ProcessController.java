package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/process")
public class ProcessController {
    private final ProcessService processService;

    @PostMapping("/sign")
    public ApiResponse<Boolean> signDocument(Long documentId){
        processService.sign(documentId);
        return ApiResponse.success(true);
    }





}
