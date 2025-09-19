package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.permission.CreatePermissionReq;
import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.dto.role.CreateRoleReq;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.repository.PermissionRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.service.PermissionService;
import com.manhhoach.EofficeFull.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permisionRepository;

    @Override
    public PermissionDto create(CreatePermissionReq req) {
        validate(null, req.getCode());
        Permission per = new Permission();
        per.setName(req.getName());
        per.setCode(req.getCode());
        per.setUrl(req.getUrl());
        per.setPriority(req.getPriority());
        per.setIsDisplayed(req.getIsDisplayed());
        permisionRepository.save(per);
        return PermissionDto.map(per);
    }

    @Override
    public PermissionDto update(Long id, CreatePermissionReq req) {
        validate(id, req.getCode());
        Permission per = permisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        per.setName(req.getName());
        per.setCode(req.getCode());
        per.setUrl(req.getUrl());
        per.setPriority(req.getPriority());
        per.setIsDisplayed(req.getIsDisplayed());
        permisionRepository.save(per);
        return PermissionDto.map(per);
    }

    @Override
    public void delete(Long id) {
        permisionRepository.deleteById(id);
    }

    @Override
    public PermissionDto getById(Long id) {
        Permission per = permisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        return PermissionDto.map(per);
    }

    @Override
    public PagedResponse<PermissionDto> getPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<Permission> perPage = permisionRepository.findAll(pageable);
        var perDtos = perPage.getContent().stream().map(per->{
            return PermissionDto.map(per);
        }).toList();

        return new PagedResponse<>(
                perDtos,
                perPage.getNumber() + 1,
                perPage.getTotalPages(),
                perPage.getTotalElements()
        );
    }

    private void validate(Long id, String code){
        if(permisionRepository.existCode(id, code)){
            throw new IllegalArgumentException("Permission code already exist");
        }
    }
}
