package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Module r WHERE r.code = :code AND (:id IS NULL OR r.id != :id)
            """)
    boolean existCode(Long id, String code);
}
