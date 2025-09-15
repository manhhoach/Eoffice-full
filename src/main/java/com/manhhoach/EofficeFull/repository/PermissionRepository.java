package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.res.PermissionDto;
import com.manhhoach.EofficeFull.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = """
            SELECT new com.manhhoach.EofficeFull.dto.res.PermissionDto( 
                p.name,
                p.code,
                p.url,
                p.isDisplay,
                p.priority)
            FROM user_role ur 
            JOIN role r ON ur.role_id = r.id
            JOIN role_permission rp ON r.id = rp.role_id
            JOIN permission p ON rp.permission_id = p.id
            WHERE ur.user_id = :userId
            """, nativeQuery = true)
    List<PermissionDto> getPermissionsByUserId(Long userId);
}
