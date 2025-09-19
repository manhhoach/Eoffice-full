package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
}
