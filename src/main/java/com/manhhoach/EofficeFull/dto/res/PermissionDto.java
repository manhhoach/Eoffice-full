package com.manhhoach.EofficeFull.dto.res;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private String name;
    private String code;
    private String url;
    private Boolean isDisplay;
    private Integer priority;
}
