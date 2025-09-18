package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.role.CreateRoleReq;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleDto create(CreateRoleReq req) {
        validate(null, req.getCode());
        Role role = new Role();
        role.setName(req.getName());
        role.setCode(req.getCode());
        roleRepository.save(role);
        return RoleDto.map(role);
    }

    @Override
    public RoleDto update(Long id, CreateRoleReq req) {
        validate(id, req.getCode());
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(req.getName());
        role.setCode(req.getCode());
        roleRepository.save(role);
        return RoleDto.map(role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto getById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return RoleDto.map(role);
    }

    @Override
    public PagedResponse<RoleDto> getPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<Role> rolePage = roleRepository.findAll(pageable);
        var roleDtos = rolePage.getContent().stream().map(role->{
            return RoleDto.map(role);
        }).toList();

        return new PagedResponse<>(
                roleDtos,
                rolePage.getNumber() + 1,
                rolePage.getTotalPages(),
                rolePage.getTotalElements()
        );
    }

    private void validate(Long id, String code){
        if(roleRepository.existCode(id, code)){
            throw new IllegalArgumentException("Role code already exist");
        }
    }
}
