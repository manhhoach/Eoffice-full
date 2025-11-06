package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.dto.outgoingDocument.CreateOutgoingDocReq;
import com.manhhoach.EofficeFull.service.OutgoingDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/out-going-documents")
@RequiredArgsConstructor
public class OutgoingDocumentController {

    private final OutgoingDocumentService outgoingDocumentService;

    @IsAuthorized(PermissionConstant.VIEW_OUT_GOING_DOCS)
    @PostMapping
    public ApiResponse<Boolean> create(@RequestBody CreateOutgoingDocReq req) throws IOException{
        outgoingDocumentService.create(req);
        return ApiResponse.success(true);
    }
}
