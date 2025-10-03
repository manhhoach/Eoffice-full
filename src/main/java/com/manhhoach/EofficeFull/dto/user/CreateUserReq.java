package com.manhhoach.EofficeFull.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserReq {
    private String username;
    private List<Long> roleIds;
}
