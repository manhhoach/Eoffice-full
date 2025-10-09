package com.manhhoach.EofficeFull.entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Document extends BaseEntity {
    private String documentNumber;
    private String summary;
}
