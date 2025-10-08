package com.manhhoach.EofficeFull.dto.processFlow;

import com.manhhoach.EofficeFull.entity.ProcessFlow;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProcessFlowDto {
    private Long id;
    private String name;

    public static ProcessFlowDto map(ProcessFlow data) {
        return ProcessFlowDto.builder().name(data.getName()).id(data.getId()).build();
    }
}
