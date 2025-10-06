package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.entity.UserRoleDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleDepartmentRepository extends JpaRepository<UserRoleDepartment, Long> {

    List<UserRoleDepartment> findByUserId(Long userId);
}
