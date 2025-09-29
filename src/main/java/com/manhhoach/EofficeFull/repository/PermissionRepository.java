package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.permission.PermissionPagingReq;
import com.manhhoach.EofficeFull.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = """
            SELECT DISTINCT p
            FROM User u
            JOIN u.roles r
            JOIN r.permissions p
            LEFT JOIN FETCH p.module m
            WHERE u.id = :userId
            """)
    List<Permission> getPermissionsByUserId(Long userId);

    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Permission r WHERE r.code = :code AND (:id IS NULL OR r.id != :id)
            """)
    boolean existCode(Long id, String code);


    @Query("""
            SELECT p from Permission p
            WHERE ( :#{#req.search} IS NULL OR :#{#req.search}=''
                    OR LOWER(p.name) LIKE LOWER(CONCAT('%', :#{#req.search}, '%'))
                    OR LOWER(p.code) LIKE LOWER(CONCAT('%', :#{#req.search}, '%'))
                    )
                    AND (:#{#req.isDisplayed} IS NULL OR :#{#req.isDisplayed} = p.isDisplayed)
                    AND p.module.id = :#{#req.moduleId}
            """)
    Page<Permission> searchPermissions(PermissionPagingReq req, Pageable pageable);
}
