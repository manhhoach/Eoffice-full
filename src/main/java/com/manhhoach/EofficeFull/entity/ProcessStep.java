package com.manhhoach.EofficeFull.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "process_step")
public class ProcessStep extends BaseEntity {

    private String name;

    private Boolean isReturn;

    private Boolean needToNote;

    private Boolean needToFile;

    private Boolean isSameDepartment;

    private Long returnType;

    private String receptionRoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startProcessStatusId", nullable = true)
    private ProcessStatus startProcessStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endProcessStatusId", nullable = true)
    private ProcessStatus endProcessStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processFlowId", nullable = true)
    private ProcessFlow processFlow;
}
