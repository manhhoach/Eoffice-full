package com.manhhoach.EofficeFull.config;

import com.manhhoach.EofficeFull.constant.RoleConstant;
import com.manhhoach.EofficeFull.entity.*;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
import com.manhhoach.EofficeFull.repository.PermissionRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            ModuleRepository moduleRepository,
            UserRepository userRepository
    ) {
        return args -> {
            if (roleRepository.count() == 0 && permissionRepository.count() == 0 && moduleRepository.count() == 0) {

                // 1. Tạo Modules
                Module dashboard = Module.builder().name("Dashboard").code("DASHBOARD").isDisplayed(true).build();
                Module userMgmt = Module.builder().name("User Management").code("USER_MGMT").isDisplayed(true).build();
                Module profile = Module.builder().name("Profile").code("PROFILE").isDisplayed(true).build();
                Module orders = Module.builder().name("Orders").code("ORDERS").isDisplayed(true).build();

                // 2. Tạo Permissions (gắn với module)
                Permission p1 = Permission.builder().name("View Dashboard").code("VIEW_DASHBOARD").url("/dashboard/view").priority(1).module(dashboard).build();
                Permission p2 = Permission.builder().name("Edit Dashboard").code("EDIT_DASHBOARD").url("/dashboard/edit").priority(2).module(dashboard).build();

                Permission p3 = Permission.builder().name("View Users").code("VIEW_USERS").url("/users/view").priority(1).module(userMgmt).build();
                Permission p4 = Permission.builder().name("Edit Users").code("EDIT_USERS").url("/users/edit").priority(2).module(userMgmt).build();

                Permission p5 = Permission.builder().name("View Profile").code("VIEW_PROFILE").url("/profile/view").priority(1).module(profile).build();
                Permission p6 = Permission.builder().name("Edit Profile").code("EDIT_PROFILE").url("/profile/edit").priority(2).module(profile).build();

                Permission p7 = Permission.builder().name("View Orders").code("VIEW_ORDERS").url("/orders/view").priority(1).module(orders).build();
                Permission p8 = Permission.builder().name("Edit Orders").code("EDIT_ORDERS").url("/orders/edit").priority(2).module(orders).build();

                // Extra permission (không gắn module)
                Permission sysSettings = Permission.builder().name("System Settings").code("SETTINGS").url("/settings").priority(1).build();
                Permission roleMgmt = Permission.builder().name("Role Management").code("ROLE_MGMT").url("/roles").priority(2).build();

                Permission notifications = Permission.builder().name("Notifications").code("NOTIFICATIONS").url("/notifications").priority(1).build();
                Permission help = Permission.builder().name("Help Center").code("HELP").url("/help").priority(2).build();

                // 3. Tạo Roles
                Role adminRole = Role.builder()
                        .name("Administrator")
                        .code(RoleConstant.ADMIN)
                        .permissions(List.of(p1, p2, p3, p4, sysSettings, roleMgmt))
                        .build();

                Role userRole = Role.builder()
                        .name("User")
                        .code(RoleConstant.USER)
                        .permissions(List.of(p5, p6, p7, p8, notifications, help))
                        .build();

                // 4. Lưu dữ liệu
                moduleRepository.saveAll(List.of(dashboard, userMgmt, profile, orders));
                permissionRepository.saveAll(List.of(p1,p2,p3,p4,p5,p6,p7,p8,sysSettings,roleMgmt,notifications,help));
                roleRepository.saveAll(List.of(adminRole, userRole));


                System.out.println("✔ Default roles, permissions, modules & users đã được khởi tạo.");
            }
        };
    }


}

