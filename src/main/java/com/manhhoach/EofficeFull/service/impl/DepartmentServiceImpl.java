package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.department.CreateDepartmentReq;
import com.manhhoach.EofficeFull.dto.department.DepartmentDto;
import com.manhhoach.EofficeFull.dto.department.DepartmentPagingReq;
import com.manhhoach.EofficeFull.entity.Department;
import com.manhhoach.EofficeFull.repository.DepartmentRepository;
import com.manhhoach.EofficeFull.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto create(CreateDepartmentReq req) {
        var data = CreateDepartmentReq.map(req);
        departmentRepository.save(data);
        return DepartmentDto.builder().name(data.getName()).id(data.getId()).build();
    }

    @Override
    public DepartmentDto update(Long id, CreateDepartmentReq req) {
        var data = departmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(""));
        data.setName(req.getName());
        departmentRepository.save(data);
        return DepartmentDto.builder().name(data.getName()).id(data.getId()).build();
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public PagedResponse<DepartmentDto> getPaged(DepartmentPagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<Department> data = departmentRepository.searchDepartment(request, pageable);
        var res = data.getContent().stream().map(e -> {
            return DepartmentDto.map(e);
        }).toList();
        return new PagedResponse<>(
                res,
                data.getNumber() + 1,
                data.getTotalPages(),
                data.getTotalElements()
        );
    }
}
