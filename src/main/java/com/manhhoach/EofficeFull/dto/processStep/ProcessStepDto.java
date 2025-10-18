package com.manhhoach.EofficeFull.dto.processStep;

import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.entity.ProcessFlow;
import com.manhhoach.EofficeFull.entity.ProcessStatus;
import com.manhhoach.EofficeFull.entity.ProcessStep;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
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

    public static ProcessStepDto map(ProcessStep data){
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

}
