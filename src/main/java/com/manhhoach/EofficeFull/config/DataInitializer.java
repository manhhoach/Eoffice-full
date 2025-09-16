package com.manhhoach.EofficeFull.config;

import com.manhhoach.EofficeFull.constant.RoleConstant;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.repository.PermissionRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return args -> {
            if (roleRepository.count() == 0 && permissionRepository.count() == 0) {
                // 1. Tạo Roles
                Role adminRole = Role.builder().name("Administrator").code("ADMIN").build();
                Role userRole = Role.builder().name("User").code("USER").build();

                // 2. Tạo Permissions
                Permission p1 = Permission.builder().name("Dashboard").code("DASHBOARD").url("/dashboard").isDisplay(true).priority(1).build();
                Permission p2 = Permission.builder().name("User Management").code("USER_MGMT").url("/users").isDisplay(true).priority(2).build();
                Permission p3 = Permission.builder().name("Role Management").code("ROLE_MGMT").url("/roles").isDisplay(true).priority(3).build();
                Permission p4 = Permission.builder().name("System Settings").code("SETTINGS").url("/settings").isDisplay(true).priority(4).build();

                Permission p5 = Permission.builder().name("Profile").code("PROFILE").url("/profile").isDisplay(true).priority(1).build();
                Permission p6 = Permission.builder().name("My Orders").code("ORDERS").url("/orders").isDisplay(true).priority(2).build();
                Permission p7 = Permission.builder().name("Notifications").code("NOTIFICATIONS").url("/notifications").isDisplay(true).priority(3).build();
                Permission p8 = Permission.builder().name("Help Center").code("HELP").url("/help").isDisplay(true).priority(4).build();

                // 3. Gán quyền cho role
                adminRole.setPermissions(Set.of(p1, p2, p3, p4));
                userRole.setPermissions(Set.of(p5, p6, p7, p8));

                // 4. Lưu DB
                permissionRepository.saveAll(List.of(p1,p2,p3,p4,p5,p6,p7,p8));
                roleRepository.saveAll(List.of(adminRole, userRole));

                System.out.println("✔ Default roles & permissions đã được khởi tạo.");
            }
        };
    }

}

