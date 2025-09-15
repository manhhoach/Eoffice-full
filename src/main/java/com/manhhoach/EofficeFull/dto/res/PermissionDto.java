package com.manhhoach.EofficeFull.dto.res;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDto {
    private String name;
    private String code;
    private String url;
    private String isDisplay;
    private Integer priority;
}
