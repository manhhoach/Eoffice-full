package com.manhhoach.EofficeFull.dto.role;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRoleStatusDto {
    private String name;
    private Long id;
    private Boolean selected;
}
