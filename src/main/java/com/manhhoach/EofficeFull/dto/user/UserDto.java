package com.manhhoach.EofficeFull.dto.user;

import com.manhhoach.EofficeFull.dto.module.ModuleDto;
import com.manhhoach.EofficeFull.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private List<ModuleDto> modules;
    private List<String> permissionCodes;


    public static UserDto map(User user){
        return UserDto.builder().id(user.getId()).username(user.getUsername()).build();
    }
}
