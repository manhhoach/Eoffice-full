package com.manhhoach.EofficeFull.dto.processStep;

import com.manhhoach.EofficeFull.common.SelectListItem;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import lombok.Data;

import java.util.List;

@Data
public class StepConfig {
    private List<RoleDto> roles;
    private List<ProcessStatusDto> statuses;
    private List<SelectListItem> returnTypes;
}
