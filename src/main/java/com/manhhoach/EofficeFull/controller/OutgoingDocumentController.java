package com.manhhoach.EofficeFull.controller;

import com.manhhoach.EofficeFull.common.ApiResponse;
import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.config.annotations.IsAuthorized;
import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.dto.department.DepartmentDto;
import com.manhhoach.EofficeFull.dto.outgoingDocument.CreateOutgoingDocReq;
import com.manhhoach.EofficeFull.dto.outgoingDocument.OutgoingDocDto;
import com.manhhoach.EofficeFull.dto.outgoingDocument.OutgoingDocPagingReq;
import com.manhhoach.EofficeFull.service.OutgoingDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/out-going-documents")
@RequiredArgsConstructor
public class OutgoingDocumentController {

    private final OutgoingDocumentService outgoingDocumentService;

    @IsAuthorized(PermissionConstant.VIEW_OUT_GOING_DOCS)
    @GetMapping
    public ApiResponse<PagedResponse<OutgoingDocDto>> getAll(OutgoingDocPagingReq req){
        var data = outgoingDocumentService.getAll(req);
        return ApiResponse.success(data);
    }



    @IsAuthorized(PermissionConstant.VIEW_OUT_GOING_DOCS)
    @PostMapping
    public ApiResponse<Boolean> create(@RequestBody CreateOutgoingDocReq req) throws IOException{
        outgoingDocumentService.create(req);
        return ApiResponse.success(true);
    }

    @IsAuthorized(PermissionConstant.VIEW_OUT_GOING_DOCS)
    @PutMapping
    public ApiResponse<Boolean> update(@RequestBody CreateOutgoingDocReq req, @PathVariable Long id) throws IOException{
        outgoingDocumentService.update(req, id);
        return ApiResponse.success(true);
    }

    @IsAuthorized(PermissionConstant.VIEW_OUT_GOING_DOCS)
    @DeleteMapping
    public ApiResponse<Void> delete(@PathVariable Long id) throws IOException{
        outgoingDocumentService.delete(id);
        return ApiResponse.success(null);
    }
}
