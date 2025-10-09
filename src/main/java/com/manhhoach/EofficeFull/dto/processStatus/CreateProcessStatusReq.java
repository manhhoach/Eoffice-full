package com.manhhoach.EofficeFull.dto.processStatus;

import lombok.Data;

@Data
public class CreateProcessStatusReq {
    private String name;
    private Boolean isStart;
    private Boolean isEnd;
}
