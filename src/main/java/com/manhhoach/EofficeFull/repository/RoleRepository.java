package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByCode(String code);

    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Role r WHERE r.code = :code AND (:id IS NULL OR r.id != :id)
            """)
    boolean existCode(Long id, String code);

    Page<Role> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
            String name, String code, Pageable pageable);

    @Query("""
            SELECT p.id FROM Role r JOIN r.permissions p  WHERE r.id = :roleId
            """)
    List<Long> findPermissionIdsByRoleId(Long roleId);
}
