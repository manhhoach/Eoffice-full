package com.manhhoach.EofficeFull.seeds;

import com.manhhoach.EofficeFull.constant.RoleConstant;
import com.manhhoach.EofficeFull.entity.Department;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.repository.DepartmentRepository;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
import com.manhhoach.EofficeFull.repository.PermissionRepository;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            ModuleRepository moduleRepository,
            DepartmentRepository departmentRepository
    ) {
        return args -> {
            if (roleRepository.count() == 0 && permissionRepository.count() == 0 && moduleRepository.count() == 0) {

                // 1. Tạo Modules
                Module adminModule = Module.builder().name("System Management").code("SYSTEM_MGMT").isDisplayed(true).build();
                Permission p1 = Permission.builder().name("User Management").code("VIEW_USERS").isDisplayed(true)
                        .url("/users").priority(1).module(adminModule).build();
                Permission p2 = Permission.builder().name("Role Management").code("VIEW_ROLES").isDisplayed(true)
                        .url("/roles").priority(2).module(adminModule).build();
                Permission p3 = Permission.builder().name("Module Management").code("VIEW_MODULES").isDisplayed(true)
                        .url("/modules").priority(3).module(adminModule).build();
                Permission p4 = Permission.builder().name("Permission Management").code("VIEW_PERMISSIONS").isDisplayed(false)
                        .module(adminModule).build();

                Module officeModule = Module.builder().name("Document Management").code("DOC_MGMT").isDisplayed(true).build();
                Permission p5 = Permission.builder().name("Outgoing docs").code("VIEW_OUT_GOING_DOCS")
                        .isDisplayed(true).url("/out-going-docs").priority(1).module(officeModule).build();
                Permission p6 = Permission.builder().name("Incoming docs").code("VIEW_IN_COMING_DOCS")
                        .isDisplayed(true).url("/in-coming-docs").priority(2).module(officeModule).build();

                // 3. Tạo Roles
                Role adminRole = Role.builder()
                        .name("Administrator")
                        .code(RoleConstant.ADMIN)
                        .permissions(List.of(p1, p2, p3, p4, p5, p6))
                        .build();

                Role userRole = Role.builder()
                        .name("User")
                        .code(RoleConstant.USER)
                        .build();

                // 4. Lưu dữ liệu
                moduleRepository.saveAll(List.of(adminModule, officeModule));
                permissionRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6));
                roleRepository.saveAll(List.of(adminRole, userRole));

                System.out.println("Default roles, permissions, modules đã được khởi tạo.");
            }
            if (departmentRepository.count() == 0) {
                Department dep1 = Department.builder()
                        .name("Human Resources")
                        .priority(1)
                        .build();

                Department dep2 = Department.builder()
                        .name("Finance")
                        .priority(2)
                        .build();

                Department dep3 = Department.builder()
                        .name("IT")
                        .priority(3)
                        .build();
                departmentRepository.saveAll(List.of(dep1, dep2, dep3));
                System.out.println("Default departments đã được khởi tạo.");

            }
        };
    }


}

