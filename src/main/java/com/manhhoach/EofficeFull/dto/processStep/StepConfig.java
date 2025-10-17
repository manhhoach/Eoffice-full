package com.manhhoach.EofficeFull.dto.processStep;

import com.manhhoach.EofficeFull.common.SelectListItem;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class StepConfig {
    private List<RoleDto> RoleDtoList;
    private List<ProcessStatusDto> processStatusDtoList;
    private List<SelectListItem> returnTypes;
}
