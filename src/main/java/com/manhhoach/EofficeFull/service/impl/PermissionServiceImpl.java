package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.permission.*;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
import com.manhhoach.EofficeFull.repository.PermissionRepository;
import com.manhhoach.EofficeFull.repository.UserRepository;
import com.manhhoach.EofficeFull.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;

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

        permissionRepository.save(per);
        return PermissionDto.map(per);
    }

    @Override
    public PermissionDto update(Long id, CreatePermissionReq req) {
        validate(id, req.getCode());

        Permission per = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        Module md = getModule(req.getModuleId());
        per.setName(req.getName());
        per.setCode(req.getCode());
        per.setUrl(req.getUrl());
        per.setPriority(req.getPriority());
        per.setIsDisplayed(req.getIsDisplayed());
        per.setModule(md);
        permissionRepository.save(per);
        return PermissionDto.map(per);
    }

    @Override
    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public PermissionDto getById(Long id) {
        Permission per = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        return PermissionDto.map(per);
    }

    @Override
    public PagedResponse<PermissionDto> getPaged(PermissionPagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<Permission> perPage = permissionRepository.searchPermissions(request, pageable);
        var perDtos = perPage.getContent().stream().map(per -> {
            return PermissionDto.map(per);
        }).toList();

        return new PagedResponse<>(
                perDtos,
                perPage.getNumber() + 1,
                perPage.getTotalPages(),
                perPage.getTotalElements()
        );
    }

    @Override
    public List<PermissionSelectionDto> getSelectedPermissions(Long userId) {
        var permissions = permissionRepository.findAll();
        var selectedPermissionIds = permissionRepository.getPermissionIdsByUserId(userId);
        return permissions.stream().map(e -> {
            var item = PermissionSelectionDto.builder()
                    .name(e.getName())
                    .id(e.getId())
                    .selected(selectedPermissionIds.contains(e.getId()))
                    .build();
            return item;
        }).toList();
    }

    @Override
    public void setSelectedPermissions(SelectedPermissionReq req) {
        var user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var permissions = permissionRepository.findAllById(req.getPermissionIds());
        user.setPermissions(permissions);
        userRepository.save(user);
    }

    private void validate(Long id, String code) {
        if (permissionRepository.existCode(id, code)) {
            throw new IllegalArgumentException("Permission code already exist");
        }
    }

    Module getModule(Long id) {
        return moduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Module not found"));
    }
}
