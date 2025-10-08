package com.manhhoach.EofficeFull.dto.department;

import com.manhhoach.EofficeFull.entity.Department;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DepartmentDto {
    private Long id;
    private String name;
    private Integer priority;

    public static DepartmentDto map(Department data) {
        return DepartmentDto.builder().id(data.getId())
                .name(data.getName())
                .priority(data.getPriority())
                .build();
    }
}
