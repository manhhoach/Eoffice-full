package com.manhhoach.EofficeFull.entity;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity {
    private String name;
    private String code;
    private String url;
    private Boolean isDisplay;
    private Integer priority;
}