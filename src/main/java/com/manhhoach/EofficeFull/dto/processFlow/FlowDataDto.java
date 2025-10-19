package com.manhhoach.EofficeFull.dto.processFlow;

import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepDto;
import lombok.Data;

import java.util.List;

@Data
public class FlowDataDto {
    private List<ProcessStepDto> steps;
    private List<ProcessStatusDto> statuses;
}
