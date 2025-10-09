package com.manhhoach.EofficeFull.dto.processStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessStatusDto {
    private Long id;
    private String name;
    private Boolean isStart;
    private Boolean isEnd;
}
