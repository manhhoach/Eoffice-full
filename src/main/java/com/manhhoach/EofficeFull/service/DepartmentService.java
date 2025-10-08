package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.department.CreateDepartmentReq;
import com.manhhoach.EofficeFull.dto.department.DepartmentDto;
import com.manhhoach.EofficeFull.dto.department.DepartmentPagingReq;

public interface DepartmentService {
    DepartmentDto create(CreateDepartmentReq req);

    DepartmentDto update(Long id, CreateDepartmentReq req);

    void delete(Long id);

    PagedResponse<DepartmentDto> getPaged(DepartmentPagingReq request);
}
