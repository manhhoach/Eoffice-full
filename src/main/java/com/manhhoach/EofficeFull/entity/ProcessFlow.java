package com.manhhoach.EofficeFull.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "process_flow")
public class ProcessFlow extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "processFlow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcessStatus> processStatuses;
}
