package com.manhhoach.EofficeFull.dto.processStep;

import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.entity.ProcessFlow;
import com.manhhoach.EofficeFull.entity.ProcessStatus;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProcessStepDto {

    private Long id; //

    private String name;//

    private Boolean isReturn;//

    private Boolean needToNote;//

    private Boolean needToFile;//

    private Boolean isSameDepartment;//

    private ProcessStatusDto end;

    private ProcessStatusDto start;

    private Long returnType;

    private List<RoleDto> receptionRoles;

}
