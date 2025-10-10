package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.processStep.CreateProcessStepReq;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepDto;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepPagingReq;
import com.manhhoach.EofficeFull.service.ProcessStepService;
import org.springframework.stereotype.Service;

@Service
public class ProcessStepServiceImpl implements ProcessStepService {
    @Override
    public ProcessStepDto create(CreateProcessStepReq req) {
        return null;
    }

    @Override
    public ProcessStepDto update(Long id, CreateProcessStepReq req) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ProcessStepDto getById(Long id) {
        return null;
    }

    @Override
    public PagedResponse<ProcessStepDto> getPaged(ProcessStepPagingReq request) {
        return null;
    }
}
