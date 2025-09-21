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
@Table(name="permission")
public class Permission extends BaseEntity {
    private String name;
    private String code;
    private String url;
    private Boolean isDisplayed;
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moduleId", nullable = false)
    private Module module;
}