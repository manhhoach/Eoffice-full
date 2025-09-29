package com.manhhoach.EofficeFull.dto.permission;

import lombok.Data;

@Data
public class CreatePermissionReq {
    private String name;
    private String code;
    private String url;
    private Boolean isDisplayed;
    private Integer priority;
    private Long moduleId;
}
