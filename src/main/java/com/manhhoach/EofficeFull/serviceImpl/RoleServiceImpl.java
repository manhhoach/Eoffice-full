package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.role.*;
import com.manhhoach.EofficeFull.entity.Department;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.entity.User;
import com.manhhoach.EofficeFull.entity.UserRoleDepartment;
import com.manhhoach.EofficeFull.repository.DepartmentRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.repository.UserRepository;
import com.manhhoach.EofficeFull.repository.UserRoleDepartmentRepository;
import com.manhhoach.EofficeFull.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleDepartmentRepository userRoleDepartmentRepository;
    private final DepartmentRepository departmentRepository;

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
    @Transactional
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
    @Transactional
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
    public List<DepartmentRolesDto> getCurrentRoles(Long userId) {
        var assignedRoles = userRoleDepartmentRepository.findByUserId(userId);
        var roles = roleRepository.findAll();
        var departments = departmentRepository.findAll();
        return departments.stream().map(dep -> {
            var statuses = roles.stream().map(role -> {
                Boolean selected = assignedRoles.stream()
                        .anyMatch(ar -> role.getId().equals(ar.getRole().getId())
                                && (ar.getDepartment() == null || dep.getId().equals(ar.getDepartment().getId()))
                                && ar.getIsActive() == true);
                return UserRoleStatusDto.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .selected(selected)
                        .build();
            }).toList();
            return DepartmentRolesDto.builder()
                    .userRoleStatuses(statuses)
                    .departmentId(dep.getId())
                    .departmentName(dep.getName())
                    .build();
        }).toList();
    }

    @Override
    public void assignRoles(AssignUserRolesReq req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        List<UserRoleDepartment> currentAssignments = userRoleDepartmentRepository.findByUserId(req.getUserId());
        Map<String, UserRoleDepartment> currentMap = currentAssignments.stream()
                .collect(Collectors.toMap(
                        e -> e.getRole().getId() + "_" + (e.getDepartment() != null ? e.getDepartment().getId() : "null"),
                        Function.identity()
                ));
        List<UserRoleDepartment> result = new ArrayList<>();

        var roleIds = req.getAssignments().stream().map(e -> e.getRoleId()).toList();
        var departmentIds = req.getAssignments().stream().map(e -> e.getDepartmentId()).toList();
        List<Role> roles = roleRepository.findAllById(roleIds);
        List<Department> departments = departmentRepository.findAllById(departmentIds);

        for (var a : req.getAssignments()) {
            Role role = roles.stream().filter(r -> r.getId().equals(a.getRoleId())).findFirst().get();
            Department department = departments.stream()
                    .filter(d -> d.getId().equals(a.getDepartmentId()))
                    .findFirst()
                    .orElse(null);
            String key = role.getId() + "_" + (department != null ? department.getId() : "null");
            UserRoleDepartment existing = currentMap.remove(key);
            if (existing != null) {
                existing.setIsActive(true);
                result.add(existing);
            } else {
                result.add(
                        UserRoleDepartment.builder()
                                .user(user)
                                .role(role)
                                .department(department)
                                .isActive(true)
                                .build());
            }

        }
        currentMap.values().forEach(e -> e.setIsActive(false));
        result.addAll(currentMap.values());
        userRoleDepartmentRepository.saveAll(result);
    }

    private void validate(Long id, String code) {
        if (roleRepository.existCode(id, code)) {
            throw new IllegalArgumentException("Role code already exist");
        }
    }
}
