package com.manhhoach.EofficeFull.dto.module;

import com.manhhoach.EofficeFull.common.PagingRequest;
import lombok.Data;

@Data
public class ModulePagingReq extends PagingRequest {
    private Boolean isDisplayed;
}
