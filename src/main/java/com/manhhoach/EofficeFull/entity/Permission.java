package com.manhhoach.EofficeFull.entity;


import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Permission extends BaseEntity {
    private String name;
    private String code;
}
