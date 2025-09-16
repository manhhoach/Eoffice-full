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
            FROM User u
            JOIN u.roles r
            JOIN r.permissions p
            WHERE u.id = :userId
            """)
    List<PermissionDto> getPermissionsByUserId(Long userId);
}
