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

    private Boolean requiredFile;

    private Boolean isSameDepartment;

    private Long returnType;

    private String receptionRoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startProcessStatusId", nullable = true)
    private ProcessStatus startProcessStatus;

    @Column(name = "startProcessStatusId", insertable = false, updatable = false)
    private Long startProcessStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endProcessStatusId", nullable = true)
    private ProcessStatus endProcessStatus;

    @Column(name = "endProcessStatusId", insertable = false, updatable = false)
    private Long endProcessStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processFlowId", nullable = true)
    private ProcessFlow processFlow;

    @Column(name = "processFlowId", insertable = false, updatable = false)
    private Long processFlowId;
}
