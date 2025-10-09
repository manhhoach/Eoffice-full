package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowDto;
import com.manhhoach.EofficeFull.dto.processStatus.CreateProcessStatusReq;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusPagingReq;
import com.manhhoach.EofficeFull.entity.ProcessFlow;
import com.manhhoach.EofficeFull.entity.ProcessStatus;
import com.manhhoach.EofficeFull.repository.ProcessFlowRepository;
import com.manhhoach.EofficeFull.repository.ProcessStatusRepository;
import com.manhhoach.EofficeFull.service.ProcessStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ProcessStatusServiceImpl implements ProcessStatusService {

    @Autowired
    private ProcessStatusRepository processStatusRepository;

    @Autowired
    private ProcessFlowRepository processFlowRepository;

    @Override
    public ProcessStatusDto create(CreateProcessStatusReq req) {
        var flow = processFlowRepository.findById(req.getProcessFlowId())
                .orElseThrow(()-> new IllegalArgumentException(""));
        var data = ProcessStatus.builder()
                .isStart(req.getIsStart())
                .isEnd(req.getIsEnd())
                .name(req.getName())
                .processFlow(flow)
                .build();
        processStatusRepository.save(data);
        return ProcessStatusDto.map(data);
    }

    @Override
    public ProcessStatusDto update(Long id, CreateProcessStatusReq req) {
        var data = processStatusRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException());
        data.setName(req.getName());
        data.setIsEnd(req.getIsEnd());
        data.setIsStart(req.getIsStart());
        processStatusRepository.save(data);
        return ProcessStatusDto.map(data);
    }

    @Override
    public void delete(Long id) {
        processStatusRepository.deleteById(id);
    }

    @Override
    public PagedResponse<ProcessStatusDto> getPaged(ProcessStatusPagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<ProcessStatus> data = processStatusRepository.search(request, pageable);
        var res = data.getContent().stream().map(e -> {
            return ProcessStatusDto.map(e);
        }).toList();
        return new PagedResponse<>(
                res,
                data.getNumber() + 1,
                data.getTotalPages(),
                data.getTotalElements()
        );
    }
}
