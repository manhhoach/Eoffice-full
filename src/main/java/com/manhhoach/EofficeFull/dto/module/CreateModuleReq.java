package com.manhhoach.EofficeFull.dto.module;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateModuleReq {
    private String name;
    private String code;
    private Boolean isDisplayed;
}
