package com.manhhoach.EofficeFull.dto.processStep;
import lombok.Data;

import java.util.List;

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
}
