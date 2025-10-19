package com.manhhoach.EofficeFull.dto.processStep;

import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.entity.ProcessStep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStepDto {

    private Long id;

    private String name;

    private Boolean isReturn;

    private Boolean needToNote;

    private Boolean requiredFile;

    private Boolean isSameDepartment;

    private ProcessStatusDto end;

    private ProcessStatusDto start;

    private Long returnType;

    private Long endProcessStatusId;

    private Long startProcessStatusId;

    public static ProcessStepDto map(ProcessStep data) {
        return ProcessStepDto.builder()
                .id(data.getId())
                .name(data.getName())
                .isReturn(data.getIsReturn())
                .isSameDepartment(data.getIsSameDepartment())
                .needToNote(data.getNeedToNote())
                .requiredFile(data.getRequiredFile())
                .returnType(data.getReturnType())
                .startProcessStatusId(data.getStartProcessStatusId())
                .endProcessStatusId(data.getEndProcessStatusId())
                .build();
    }

    public ProcessStepDto(Long id, String name, Long startProcessStatusId, Long endProcessStatusId) {
        this.id = id;
        this.name = name;
        this.startProcessStatusId = startProcessStatusId;
        this.endProcessStatusId = endProcessStatusId;
    }

}
