package com.manhhoach.EofficeFull.dto.processStep;

import com.manhhoach.EofficeFull.entity.ProcessStep;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class CreateProcessStepReq {
    private String name;

    private Boolean isReturn;

    private Boolean needToNote;

    private Boolean requiredFile;

    private Boolean isSameDepartment;

    private Long returnType;

    private List<Long> receptionRoles;

    private Long startProcessStatusId;

    private Long endProcessStatusId;

    private Long processFlowId;


    public static ProcessStep map(CreateProcessStepReq data) {
        return ProcessStep.builder()
                .name(data.getName())
                .isReturn(data.getIsReturn())
                .isSameDepartment(data.getIsSameDepartment())
                .needToNote(data.getNeedToNote())
                .requiredFile(data.getRequiredFile())
                .returnType(data.getReturnType())
                .receptionRoles(Optional.ofNullable(data.getReceptionRoles())
                        .orElse(List.of())
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(","))
                ).build();
    }
}
