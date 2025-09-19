package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = """
            SELECT new com.manhhoach.EofficeFull.dto.permission.PermissionDto( 
                p.id,
                p.name,
                p.code,
                p.url,
                p.isDisplayed,
                p.priority
                )
            FROM User u
            JOIN u.roles r
            JOIN r.permissions p
            WHERE u.id = :userId
            """)
    List<PermissionDto> getPermissionsByUserId(Long userId);

    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Permission r WHERE r.code = :code AND (:id IS NULL OR r.id != :id)
            """)
    boolean existCode(Long id, String code);
}
