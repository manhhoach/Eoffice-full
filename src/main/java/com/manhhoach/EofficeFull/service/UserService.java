package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.role.*;
import com.manhhoach.EofficeFull.dto.user.CreateUserReq;
import com.manhhoach.EofficeFull.dto.user.*;

import java.util.List;

public interface UserService {
    UserDto create(CreateUserReq req);

    UserDto update(Long id, CreateUserReq req);

    void delete(Long id);

    UserDto getById(Long id);

    PagedResponse<UserDto> getPaged(UserPagingReq request);

}
