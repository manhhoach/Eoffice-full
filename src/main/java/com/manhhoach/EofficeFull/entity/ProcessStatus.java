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
@Table(name = "process_status")
public class ProcessStatus extends BaseEntity{
    private String name;
    private Boolean isStart;
    private Boolean isEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processFlowId", nullable = true)
    private ProcessFlow processFlow;
}
