package com.manhhoach.EofficeFull.dto.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateModuleReq {
    private String name;
    private String code;
    private Boolean isDisplayed;
}
