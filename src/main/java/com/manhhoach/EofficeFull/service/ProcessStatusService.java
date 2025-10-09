package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.processStatus.CreateProcessStatusReq;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusPagingReq;

public interface ProcessStatusService {
    ProcessStatusDto create(CreateProcessStatusReq req);

    ProcessStatusDto update(Long id, CreateProcessStatusReq req);

    void delete(Long id);

    PagedResponse<ProcessStatusDto> getPaged(ProcessStatusPagingReq request);
}
