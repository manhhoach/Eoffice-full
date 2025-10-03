package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Page<User> findByUsernameContainingIgnoreCase(
            String name, Pageable pageable);



    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM User u WHERE u.username = :username AND (:id IS NULL OR u.id != :id)
            """)
    boolean checkExistUsername(Long id, String username);


}
