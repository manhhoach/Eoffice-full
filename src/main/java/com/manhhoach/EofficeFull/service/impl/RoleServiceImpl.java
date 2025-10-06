package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.role.*;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.entity.User;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.repository.UserRepository;
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
    private final UserRepository userRepository;

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
    public PagedResponse<RoleDto> getPaged(RolePagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<Role> rolePage;
        if (request.getSearch() == null || request.getSearch().isBlank()) {
            rolePage = roleRepository.findAll(pageable);
        } else {
            rolePage = roleRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
                    request.getSearch(), request.getSearch(), pageable);
        }
        var roleDtos = rolePage.getContent().stream().map(role -> {
            return RoleDto.map(role);
        }).toList();

        return new PagedResponse<>(
                roleDtos,
                rolePage.getNumber() + 1,
                rolePage.getTotalPages(),
                rolePage.getTotalElements()
        );
    }

    @Override
    public List<RoleSelectionDto> getSelectedRoles(Long userId) {
        var selectedRoleIds = List.of();// roleRepository.findRoleIdsByUserId(userId);
        var roles = roleRepository.findAll();
        return roles.stream().map(e -> {
            var roleItem = RoleSelectionDto.builder()
                    .id(e.getId())
                    .name(e.getName())
                    .selected(selectedRoleIds.contains(e.getId()))
                    .build();
            return roleItem;
        }).toList();
    }

    @Override
    public void setSelectedRoles(SelectedRoleReq req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        List<Role> roles = roleRepository.findAllById(req.getRoleIds());
       // user.setRoles(roles);
        userRepository.save(user);
    }

    private void validate(Long id, String code) {
        if (roleRepository.existCode(id, code)) {
            throw new IllegalArgumentException("Role code already exist");
        }
    }
}
