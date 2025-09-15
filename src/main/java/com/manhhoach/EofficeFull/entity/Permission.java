package com.manhhoach.EofficeFull.entity;


import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Permission extends BaseEntity {
    private String name;
    private String code;
    private String url;
    private String isDisplay;
    private Integer priority;
}