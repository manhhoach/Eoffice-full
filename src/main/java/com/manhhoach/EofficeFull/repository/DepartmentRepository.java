package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.department.DepartmentPagingReq;
import com.manhhoach.EofficeFull.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("""
            SELECT d from Department d WHERE (
            :#{#req.search} IS NULL OR :#{#req.search} = '' OR LOWER(d.name) LIKE LOWER(CONCAT('%', :#{#req.search}, '%'))
            )
            """)
    Page<Department> searchDepartment(DepartmentPagingReq req, Pageable pageable);
}
