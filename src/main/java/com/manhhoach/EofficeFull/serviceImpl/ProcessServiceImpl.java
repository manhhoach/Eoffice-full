package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.config.CustomUserDetails;
import com.manhhoach.EofficeFull.constant.ProcessFlowConstant;
import com.manhhoach.EofficeFull.constant.ProcessProgressType;
import com.manhhoach.EofficeFull.entity.ProcessProgress;
import com.manhhoach.EofficeFull.repository.ProcessFlowRepository;
import com.manhhoach.EofficeFull.repository.ProcessProgressRepository;
import com.manhhoach.EofficeFull.repository.ProcessStatusRepository;
import com.manhhoach.EofficeFull.repository.ProcessStepRepository;
import com.manhhoach.EofficeFull.service.ProcessService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProcessServiceImpl implements ProcessService {

    private final ProcessProgressRepository processProgressRepository;
    private final ProcessFlowRepository processFlowRepository;
    private final ProcessStatusRepository processStatusRepository;
    private final ProcessStepRepository processStepRepository;

    @Override
    public void initProcess(Long documentId, ProcessProgressType type, String flowCode) {

        var startStep = processStatusRepository.getStartStatusByFlowCode(flowCode)
                .orElseThrow(()-> new EntityNotFoundException());

        var currentUser = CustomUserDetails.getCurrentUserDetails();

        var progress = ProcessProgress.builder()
                .type(type)
                .isProcessed(false)
                .processStatus(startStep)
                .itemId(documentId)
                .handlerId(currentUser.getId())
                .build();
        processProgressRepository.save(progress);
    }
}
