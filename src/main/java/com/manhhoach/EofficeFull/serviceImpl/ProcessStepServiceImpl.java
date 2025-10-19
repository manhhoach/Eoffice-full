package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.common.SelectListItem;
import com.manhhoach.EofficeFull.constant.ReturnTypeConstant;
import com.manhhoach.EofficeFull.dto.processStep.CreateProcessStepReq;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepDto;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepPagingReq;
import com.manhhoach.EofficeFull.dto.processStep.StepConfig;
import com.manhhoach.EofficeFull.entity.ProcessStep;
import com.manhhoach.EofficeFull.repository.ProcessFlowRepository;
import com.manhhoach.EofficeFull.repository.ProcessStatusRepository;
import com.manhhoach.EofficeFull.repository.ProcessStepRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.service.ProcessStepService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessStepServiceImpl implements ProcessStepService {

    private final ProcessStepRepository processStepRepository;
    private final ProcessStatusRepository processStatusRepository;
    private final RoleRepository roleRepository;
    private final ProcessFlowRepository processFlowRepository;

    @Override
    public void create(CreateProcessStepReq req) {
        var startStatus = processStatusRepository.findById(req.getStartProcessStatusId())
                .orElseThrow(() -> new EntityNotFoundException("Start status not found"));

        var endStatus = processStatusRepository.findById(req.getEndProcessStatusId())
                .orElseThrow(() -> new EntityNotFoundException("End status not found"));

        var flow = processFlowRepository.findById(req.getProcessFlowId())
                .orElseThrow(() -> new EntityNotFoundException("Process flow not found"));

        var entity = CreateProcessStepReq.map(req);
        entity.setProcessFlow(flow);
        entity.setEndProcessStatus(endStatus);
        entity.setStartProcessStatus(startStatus);
        processStepRepository.save(entity);
    }

    @Override
    public void update(Long id, CreateProcessStepReq req) {
        var entity = processStepRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Step not found"));

        var startStatus = processStatusRepository.findById(req.getStartProcessStatusId())
                .orElseThrow(() -> new EntityNotFoundException("Start status not found"));

        var endStatus = processStatusRepository.findById(req.getEndProcessStatusId())
                .orElseThrow(() -> new EntityNotFoundException("End status not found"));

        var flow = processFlowRepository.findById(req.getProcessFlowId())
                .orElseThrow(() -> new EntityNotFoundException("Process flow not found"));

        entity.setProcessFlow(flow);
        entity.setEndProcessStatus(endStatus);
        entity.setStartProcessStatus(startStatus);
        entity.setName(req.getName());
        entity.setIsReturn(req.getIsReturn());
        entity.setIsSameDepartment(req.getIsSameDepartment());
        entity.setNeedToNote(req.getNeedToNote());
        entity.setRequiredFile(req.getRequiredFile());
        entity.setReturnType(req.getReturnType());
        entity.setReceptionRoles(Optional.ofNullable(req.getReceptionRoles())
                .orElse(List.of())
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));

        processStepRepository.save(entity);

    }

    @Override
    public void delete(Long id) {
        processStepRepository.deleteById(id);
    }

    @Override
    public ProcessStepDto getById(Long id) {
        return null;
    }

    @Override
    public PagedResponse<ProcessStepDto> getPaged(ProcessStepPagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<ProcessStep> data = processStepRepository.search(request, pageable);
        var res = data.getContent().stream().map(e -> {
            return ProcessStepDto.map(e);
        }).toList();
        return new PagedResponse<>(
                res,
                data.getNumber() + 1,
                data.getTotalPages(),
                data.getTotalElements()
        );
    }

    @Override
    public StepConfig getConfigStep(Long flowId) {
        StepConfig data = new StepConfig();
        data.setStatuses(processStatusRepository.getByFlowId(flowId));
        data.setRoles(roleRepository.getAll());
        data.setReturnTypes(List.of(
                new SelectListItem(ReturnTypeConstant.RETURN_FIRST_HANDLER, "Return first handler"),
                new SelectListItem(ReturnTypeConstant.RETURN_PREVIOUS_HANDLER, "Return previous handler")
        ));
        return data;
    }
}
