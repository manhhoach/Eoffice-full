package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.processStatus.ChangePositionDto;
import com.manhhoach.EofficeFull.dto.processStatus.CreateProcessStatusReq;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusPagingReq;
import com.manhhoach.EofficeFull.entity.ProcessStatus;
import com.manhhoach.EofficeFull.repository.ProcessFlowRepository;
import com.manhhoach.EofficeFull.repository.ProcessStatusRepository;
import com.manhhoach.EofficeFull.service.ProcessStatusService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProcessStatusServiceImpl implements ProcessStatusService {
    private final ProcessStatusRepository processStatusRepository;

    private final ProcessFlowRepository processFlowRepository;

    @Override
    public ProcessStatusDto create(CreateProcessStatusReq req) {
        var flow = processFlowRepository.findById(req.getProcessFlowId())
                .orElseThrow(() -> new IllegalArgumentException(""));
        var data = ProcessStatus.builder()
                .isStart(req.getIsStart())
                .isEnd(req.getIsEnd())
                .name(req.getName())
                .processFlow(flow)
                .build();
        processStatusRepository.save(data);
        return ProcessStatusDto.map(data);
    }

    @Transactional
    @Override
    public ProcessStatusDto update(Long id, CreateProcessStatusReq req) {
        var data = processStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
        data.setName(req.getName());
        data.setIsEnd(req.getIsEnd());
        data.setIsStart(req.getIsStart());
        processStatusRepository.save(data);
        return ProcessStatusDto.map(data);
    }

    @Override
    @Transactional
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

    @Override
    public List<ProcessStatusDto> getListByFlowId(Long flowId) {
        var data = processStatusRepository.getByFlowId(flowId);
        return data;
    }

    @Override
    public void updatePosition(ChangePositionDto data) {
        var status = processStatusRepository.findById(data.getId()).orElseThrow(()->new EntityNotFoundException());
        status.setY(data.getY());
        status.setX(data.getX());
        processStatusRepository.save(status);
    }
}
