package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.permission.CreatePermissionReq;
import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.dto.permission.PermissionPagingReq;
import com.manhhoach.EofficeFull.dto.role.CreateRoleReq;
import com.manhhoach.EofficeFull.dto.role.RoleDto;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
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
    private final ModuleRepository moduleRepository;

    @Override
    public PermissionDto create(CreatePermissionReq req) {
        validate(null, req.getCode());
        Module md = getModule(req.getModuleId());

        Permission per = new Permission();
        per.setName(req.getName());
        per.setCode(req.getCode());
        per.setUrl(req.getUrl());
        per.setPriority(req.getPriority());
        per.setIsDisplayed(req.getIsDisplayed());
        per.setModule(md);

        permisionRepository.save(per);
        return PermissionDto.map(per);
    }

    @Override
    public PermissionDto update(Long id, CreatePermissionReq req) {
        validate(id, req.getCode());

        Permission per = permisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        Module md = getModule(req.getModuleId());
        per.setName(req.getName());
        per.setCode(req.getCode());
        per.setUrl(req.getUrl());
        per.setPriority(req.getPriority());
        per.setIsDisplayed(req.getIsDisplayed());
        per.setModule(md);
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
    public PagedResponse<PermissionDto> getPaged(PermissionPagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<Permission> perPage = permisionRepository.searchPermissions(request, pageable);
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

    Module getModule(Long id){
        return moduleRepository.findById(id).orElseThrow(()-> new RuntimeException("Module not found"));
    }
}
