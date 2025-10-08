package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.module.CreateModuleReq;
import com.manhhoach.EofficeFull.dto.module.ModuleDto;
import com.manhhoach.EofficeFull.dto.module.ModulePagingReq;
import com.manhhoach.EofficeFull.dto.processFlow.CreateProcessFlowReq;
import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowDto;
import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowPagingReq;

public interface ProcessFlowService {
    ProcessFlowDto create(CreateProcessFlowReq req);

    ProcessFlowDto update(Long id, CreateProcessFlowReq req);

    void delete(Long id);

    PagedResponse<ProcessFlowDto> getPaged(ProcessFlowPagingReq request);
}
