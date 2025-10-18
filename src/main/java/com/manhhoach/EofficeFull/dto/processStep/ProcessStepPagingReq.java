package com.manhhoach.EofficeFull.dto.processStep;

import com.manhhoach.EofficeFull.common.PagingRequest;
import lombok.Data;

@Data
public class ProcessStepPagingReq extends PagingRequest {
    private Long processFlowId;
}
