package com.manhhoach.EofficeFull.dto.department;

import com.manhhoach.EofficeFull.entity.Department;
import lombok.Data;

@Data
public class CreateDepartmentReq {
    private String name;
    private Integer priority;

    public static Department map(CreateDepartmentReq data) {
        return Department.builder().name(data.getName()).priority(data.getPriority()).build();
    }
}
