package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.processFlow.CreateProcessFlowReq;
import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowDto;
import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowPagingReq;
import com.manhhoach.EofficeFull.entity.ProcessFlow;
import com.manhhoach.EofficeFull.repository.ProcessFlowRepository;
import com.manhhoach.EofficeFull.service.ProcessFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessFlowServiceImpl implements ProcessFlowService {

    @Autowired
    private ProcessFlowRepository processFlowRepository;

    @Override
    public ProcessFlowDto create(CreateProcessFlowReq req) {
        var data = CreateProcessFlowReq.map(req);
        processFlowRepository.save(data);
        return ProcessFlowDto.builder().name(data.getName()).id(data.getId()).build();
    }

    @Override
    @Transactional
    public ProcessFlowDto update(Long id, CreateProcessFlowReq req) {
        var data = processFlowRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(""));
        data.setName(req.getName());
        processFlowRepository.save(data);
        return ProcessFlowDto.builder().name(data.getName()).id(data.getId()).build();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        processFlowRepository.deleteById(id);
    }

    @Override
    public PagedResponse<ProcessFlowDto> getPaged(ProcessFlowPagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<ProcessFlow> data = processFlowRepository.searchFlow(request, pageable);
        var res = data.getContent().stream().map(e -> {
            return ProcessFlowDto.map(e);
        }).toList();
        return new PagedResponse<>(
                res,
                data.getNumber() + 1,
                data.getTotalPages(),
                data.getTotalElements()
        );
    }
}
