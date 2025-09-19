package com.manhhoach.EofficeFull.config;

import com.manhhoach.EofficeFull.constant.RoleConstant;
import com.manhhoach.EofficeFull.entity.*;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
import com.manhhoach.EofficeFull.repository.PermissionRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, PermissionRepository permissionRepository, ModuleRepository moduleRepository) {
        return args -> {
            if (roleRepository.count() == 0 && permissionRepository.count() == 0 && moduleRepository.count() == 0) {
                // 1. Tạo Roles
                Role adminRole = Role.builder().name("Administrator").code(RoleConstant.ADMIN).build();
                Role userRole = Role.builder().name("User").code(RoleConstant.USER).build();

                // 2. Tạo Menus
                Module menu1 = Module.builder().name("Dashboard").code("DASHBOARD").isDisplayed(true).build();
                Module menu2 = Module.builder().name("User Management").code("USER_MGMT").isDisplayed(true).build();
                Module menu3 = Module.builder().name("Profile").code("PROFILE").isDisplayed(true).build();
                Module menu4 = Module.builder().name("Orders").code("ORDERS").isDisplayed(true).build();

                // 3. Tạo Permissions cho Menu
                Permission p1 = Permission.builder().name("View Dashboard").code("VIEW_DASHBOARD").url("/dashboard/view").isDisplayed(true).priority(1).module(menu1).build();
                Permission p2 = Permission.builder().name("Edit Dashboard").code("EDIT_DASHBOARD").url("/dashboard/edit").isDisplayed(true).priority(2).module(menu1).build();

                Permission p3 = Permission.builder().name("View Users").code("VIEW_USERS").url("/users/view").isDisplayed(true).priority(1).module(menu2).build();
                Permission p4 = Permission.builder().name("Edit Users").code("EDIT_USERS").url("/users/edit").isDisplayed(true).priority(2).module(menu2).build();

                Permission p5 = Permission.builder().name("View Profile").code("VIEW_PROFILE").url("/profile/view").isDisplayed(true).priority(1).module(menu3).build();
                Permission p6 = Permission.builder().name("Edit Profile").code("EDIT_PROFILE").url("/profile/edit").isDisplayed(true).priority(2).module(menu3).build();

                Permission p7 = Permission.builder().name("View Orders").code("VIEW_ORDERS").url("/orders/view").isDisplayed(true).priority(1).module(menu4).build();
                Permission p8 = Permission.builder().name("Edit Orders").code("EDIT_ORDERS").url("/orders/edit").isDisplayed(true).priority(2).module(menu4).build();

                // 4. Tạo Permissions riêng cho Role
                Permission rAdminExtra1 = Permission.builder().name("System Settings").code("SETTINGS").url("/settings").isDisplayed(true).priority(1).build();
                Permission rAdminExtra2 = Permission.builder().name("Role Management").code("ROLE_MGMT").url("/roles").isDisplayed(true).priority(2).build();

                Permission rUserExtra1 = Permission.builder().name("Notifications").code("NOTIFICATIONS").url("/notifications").isDisplayed(true).priority(1).build();
                Permission rUserExtra2 = Permission.builder().name("Help Center").code("HELP").url("/help").isDisplayed(true).priority(2).build();

                // 5. Gán permissions cho menu
                menu1.setPermissions(List.of(p1, p2));
                menu2.setPermissions(List.of(p3, p4));
                menu3.setPermissions(List.of(p5, p6));
                menu4.setPermissions(List.of(p7, p8));

                // 6. Gán permissions cho role
                adminRole.setPermissions(List.of(p1, p2, p3, p4, rAdminExtra1, rAdminExtra2));
                userRole.setPermissions(List.of(p5, p6, p7, p8, rUserExtra1, rUserExtra2));

                // 7. Gán menu cho role (nếu cần)
                adminRole.setModules(List.of(menu1, menu2));
                userRole.setModules(List.of(menu3, menu4));

                // 8. Lưu dữ liệu
                moduleRepository.saveAll(List.of(menu1, menu2, menu3, menu4));
                permissionRepository.saveAll(List.of(p1,p2,p3,p4,p5,p6,p7,p8,rAdminExtra1,rAdminExtra2,rUserExtra1,rUserExtra2));
                roleRepository.saveAll(List.of(adminRole, userRole));

                System.out.println("✔ Default roles, menus & permissions đã được khởi tạo.");
            }

        };
    }

}

