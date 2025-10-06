package com.manhhoach.EofficeFull.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_role_department",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id", "department_id"})
)
public class UserRoleDepartment extends BaseEntity {

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = true)
    private Department department;
}

