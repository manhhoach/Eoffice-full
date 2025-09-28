package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.module.ModuleDto;
import com.manhhoach.EofficeFull.dto.module.ModulePagingReq;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Module r WHERE r.code = :code AND (:id IS NULL OR r.id != :id)
            """)
    boolean existCode(Long id, String code);

    @Query("""
       SELECT m FROM Module m
       WHERE (:#{#req.search} IS NULL OR :#{#req.search} = '' 
              OR LOWER(m.name) LIKE LOWER(CONCAT('%', :#{#req.search}, '%')) 
              OR LOWER(m.code) LIKE LOWER(CONCAT('%', :#{#req.search}, '%')))
       AND (:#{#req.isDisplayed} IS NULL OR m.isDisplayed = :#{#req.isDisplayed})
       """)
    Page<Module> searchModules(ModulePagingReq req,
                               Pageable pageable);

}
