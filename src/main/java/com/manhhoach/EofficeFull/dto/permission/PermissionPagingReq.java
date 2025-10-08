package com.manhhoach.EofficeFull.dto.permission;

import com.manhhoach.EofficeFull.common.PagingRequest;
import lombok.Data;

@Data
public class PermissionPagingReq extends PagingRequest {
    private Boolean isDisplayed;
    private long moduleId;
}
