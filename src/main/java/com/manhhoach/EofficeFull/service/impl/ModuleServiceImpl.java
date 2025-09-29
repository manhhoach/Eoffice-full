package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.module.*;
import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.dto.permission.PermissionSelectionDto;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
import com.manhhoach.EofficeFull.repository.PermissionRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public ModuleDto create(CreateModuleReq req) {
        validate(null, req.getCode());
        Module data = new Module();
        data.setName(req.getName());
        data.setCode(req.getCode());
        data.setIsDisplayed(req.getIsDisplayed());
        moduleRepository.save(data);
        return ModuleDto.map(data);
    }

    @Override
    public ModuleDto update(Long id, CreateModuleReq req) {
        validate(id, req.getCode());
        Module data = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found"));
        data.setName(req.getName());
        data.setCode(req.getCode());
        data.setIsDisplayed(req.getIsDisplayed());
        moduleRepository.save(data);
        return ModuleDto.map(data);
    }

    @Override
    public void delete(Long id) {
        moduleRepository.deleteById(id);
    }

    @Override
    public ModuleDto getById(Long id) {
        Module data = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found"));
        return ModuleDto.map(data);
    }

    @Override
    public PagedResponse<ModuleDto> getPaged(ModulePagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<Module> data = moduleRepository.searchModules(request, pageable);

        var res = data.getContent().stream().map(d -> {
            return ModuleDto.map(d);
        }).toList();

        return new PagedResponse<>(
                res,
                data.getNumber() + 1,
                data.getTotalPages(),
                data.getTotalElements()
        );
    }

    @Override
    public List<ModuleSelectionDto> getSelectedModules(Long roleId) {
        List<Module> modules = moduleRepository.findAllWithPermissions();
        List<Long> selectedPermissionIds = roleRepository.findPermissionIdsByRoleId(roleId);
        List<ModuleSelectionDto> data = modules.stream().map(module -> {
            ModuleSelectionDto selectedM = ModuleSelectionDto.builder().name(module.getName()).build();

            List<PermissionSelectionDto> pDtos = module.getPermissions().stream().map(p -> {
                boolean isSelected = selectedPermissionIds.contains(p.getId());
                PermissionSelectionDto selectedP = PermissionSelectionDto.builder()
                        .id(p.getId())
                        .selected(isSelected)
                        .name(p.getName())
                        .build();

                return selectedP;

            }).toList();

            selectedM.setPermissionSelectionDtos(pDtos);
            return selectedM;
        }).toList();
        return data;
    }

    @Override
    public void setSelectedModules(SelectedModuleReq req) {
        Role role = roleRepository.findById(req.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        List<Permission> newPermissions = permissionRepository.findAllById(req.getPermissionIds());

        // Xác định permissions cần xoá
        Set<Permission> toRemove = new HashSet<>(role.getPermissions());
        toRemove.removeAll(newPermissions); // còn lại là những permission không có trong newPermissions

        // Xoá những permission dư
        role.getPermissions().removeAll(toRemove);

        // Thêm permission mới
        Set<Permission> toAdd = new HashSet<>(newPermissions);
        toAdd.removeAll(role.getPermissions()); // loại bỏ những permission đã có
        role.getPermissions().addAll(toAdd);

        roleRepository.save(role);
    }


    private void validate(Long id, String code) {
        if (moduleRepository.existCode(id, code)) {
            throw new IllegalArgumentException("Module code already exist");
        }
    }
}
