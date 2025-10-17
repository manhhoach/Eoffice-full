package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.processStep.CreateProcessStepReq;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepDto;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepPagingReq;
import com.manhhoach.EofficeFull.dto.processStep.StepConfig;

public interface ProcessStepService {
    void create(CreateProcessStepReq req);

    void update(Long id, CreateProcessStepReq req);

    void delete(Long id);

    ProcessStepDto getById(Long id);

    PagedResponse<ProcessStepDto> getPaged(ProcessStepPagingReq request);

    StepConfig getConfigStep(Long flowId);
}
