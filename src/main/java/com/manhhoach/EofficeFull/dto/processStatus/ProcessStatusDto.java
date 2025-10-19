package com.manhhoach.EofficeFull.dto.processStatus;

import com.manhhoach.EofficeFull.entity.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStatusDto {
    private Long id;
    private String name;
    private Boolean isStart;
    private Boolean isEnd;

    public static ProcessStatusDto map(ProcessStatus data) {
        return ProcessStatusDto.builder()
                .id(data.getId())
                .isStart(data.getIsStart())
                .isEnd(data.getIsEnd())
                .name(data.getName())
                .build();
    }

    public ProcessStatusDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
