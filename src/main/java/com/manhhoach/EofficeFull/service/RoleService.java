package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.dto.role.CreateRoleReq;
import com.manhhoach.EofficeFull.entity.Role;

import java.util.List;

public interface RoleService {
    Role create(CreateRoleReq req);
    Role update(Long id, CreateRoleReq req);
    void delete(Long id);
    Role getById(Long id);
    List<Role> getAll();
}
