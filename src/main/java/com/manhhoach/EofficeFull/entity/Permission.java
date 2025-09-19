package com.manhhoach.EofficeFull.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @ManyToOne
    @JoinColumn(name = "moduleId")
    private Module module;
}