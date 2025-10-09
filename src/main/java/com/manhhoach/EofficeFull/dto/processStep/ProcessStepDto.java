package com.manhhoach.EofficeFull.dto.processStep;

import com.manhhoach.EofficeFull.entity.ProcessFlow;
import com.manhhoach.EofficeFull.entity.ProcessStatus;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessStepDto {

    private Long id;

    private String name;

    private Boolean isReturn;

    private Boolean needToNote;

    private Boolean needToFile;

    private Boolean isSameDepartment;
}
