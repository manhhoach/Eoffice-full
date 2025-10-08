package com.manhhoach.EofficeFull.dto.processFlow;

import com.manhhoach.EofficeFull.entity.ProcessFlow;
import lombok.Data;

@Data
public class CreateProcessFlowReq {
    private String name;

    public static ProcessFlow map(CreateProcessFlowReq data){
        return ProcessFlow.builder().name(data.getName()).build();
    }
}
