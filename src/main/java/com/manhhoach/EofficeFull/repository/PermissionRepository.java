package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.permission.PermissionModuleDto;
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
            SELECT DISTINCT new com.manhhoach.EofficeFull.dto.permission.PermissionModuleDto( p.id, p.name, p.code, p.url, p.is_displayed, p.priority,
                                      m.id as moduleId, m.name as moduleName, m.code as moduleCode, m.is_displayed as moduleIsDisplayed)
                                FROM user_permission up
                                JOIN permission p ON up.permission_id = p.id
                                LEFT JOIN module m ON p.module_id = m.id
                                WHERE up.user_id = :userId
            
                                UNION
            
            SELECT DISTINCT new com.manhhoach.EofficeFull.dto.permission.PermissionModuleDto( p2.id, p2.name, p2.code, p2.url, p2.is_displayed, p2.priority,
                                      m2.id as moduleId, m2.name as moduleName, m2.code as moduleCode, m2.is_displayed as moduleIsDisplayed)
                                FROM user_role ur
                                JOIN role_permission rp ON ur.role_id = rp.role_id
                                JOIN permission p2 ON rp.permission_id = p2.id
                                LEFT JOIN module m2 ON p2.module_id = m2.id
                                WHERE ur.user_id = :userId;
            """)
    List<PermissionModuleDto> getPermissionsByUserId(Long userId);

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


    @Query("""
            SELECT p.id FROM User u join u.permissions p WHERE u.id = :userId
            """)
    List<Long> getPermissionIdsByUserId(Long userId);
}
