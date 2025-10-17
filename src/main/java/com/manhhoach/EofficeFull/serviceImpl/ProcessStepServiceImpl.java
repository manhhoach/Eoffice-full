package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.common.SelectListItem;
import com.manhhoach.EofficeFull.constant.ReturnTypeConstant;
import com.manhhoach.EofficeFull.dto.processStep.CreateProcessStepReq;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepDto;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepPagingReq;
import com.manhhoach.EofficeFull.dto.processStep.StepConfig;
import com.manhhoach.EofficeFull.repository.ProcessStatusRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.service.ProcessStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessStepServiceImpl implements ProcessStepService {


    private final ProcessStatusRepository processStatusRepository;
    private final RoleRepository roleRepository;

    @Override
    public ProcessStepDto create(CreateProcessStepReq req) {
        var startStatus = processStatusRepository.findById(req.getStartProcessStatusId());
        var endStatus = processStatusRepository.findById(req.getEndProcessStatusId());
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

    @Override
    public StepConfig getConfigStep(Long flowId) {
        StepConfig data = new StepConfig();
        data.setProcessStatusDtoList(processStatusRepository.getByFlowId(flowId));
        data.setRoleDtoList(roleRepository.getAll());
        data.setReturnTypes(List.of(
                new SelectListItem(ReturnTypeConstant.RETURN_FIRST_HANDLER, "Return first handler"),
                new SelectListItem(ReturnTypeConstant.RETURN_PREVIOUS_HANDLER, "Return previous handler")
        ));
        return data;
    }
}
