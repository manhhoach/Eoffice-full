package com.manhhoach.EofficeFull.seeds;

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
                Module adminModule = Module.builder().name("System Management").code("SYSTEM_MGMT").isDisplayed(true).build();

                Permission p1 = Permission.builder().name("User Management").code("VIEW_USERS").isDisplayed(true).url("/users").priority(1).module(adminModule).build();
                Permission p2 = Permission.builder().name("Role Management").code("VIEW_ROLES").isDisplayed(true).url("/roles").priority(2).module(adminModule).build();
                Permission p3 = Permission.builder().name("Module Management").code("VIEW_MODULES").isDisplayed(true).url("/modules").priority(3).module(adminModule).build();

                // 3. Tạo Roles
                Role adminRole = Role.builder()
                        .name("Administrator")
                        .code(RoleConstant.ADMIN)
                        .permissions(List.of(p1, p2, p3))
                        .build();

                Role userRole = Role.builder()
                        .name("User")
                        .code(RoleConstant.USER)
                        .build();

                // 4. Lưu dữ liệu
                moduleRepository.saveAll(List.of(adminModule));
                permissionRepository.saveAll(List.of(p1,p2,p3));
                roleRepository.saveAll(List.of(adminRole, userRole));


                System.out.println("✔ Default roles, permissions, modules & users đã được khởi tạo.");
            }
        };
    }


}

