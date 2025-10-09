package com.manhhoach.EofficeFull.dto.processStatus;

import com.manhhoach.EofficeFull.common.PagingRequest;
import lombok.Builder;
import lombok.Data;

@Data
public class ProcessStatusPagingReq extends PagingRequest {
    private Long processFlowId;
}
