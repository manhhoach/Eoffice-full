package com.manhhoach.EofficeFull.seeds;

import com.manhhoach.EofficeFull.constant.PermissionConstant;
import com.manhhoach.EofficeFull.constant.RoleConstant;
import com.manhhoach.EofficeFull.entity.Department;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.entity.Permission;
import com.manhhoach.EofficeFull.entity.Role;
import com.manhhoach.EofficeFull.repository.*;
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
            DepartmentRepository departmentRepository,
            ProcessFlowRepository processFlowRepository,
            ProcessStepRepository processStepRepository,
            ProcessStatusRepository processStatusRepository
    ) {
        return args -> {
            if (roleRepository.count() == 0 && permissionRepository.count() == 0 && moduleRepository.count() == 0) {

                // 1. Tạo Modules
                Module adminModule = Module.builder().name("System Management").code("SYSTEM_MGMT").isDisplayed(true).build();

                Permission p1 = Permission.builder().name("User Management").code(PermissionConstant.VIEW_USERS).isDisplayed(true)
                        .url("/users").priority(1).module(adminModule).build();

                Permission p2 = Permission.builder().name("Role Management").code(PermissionConstant.VIEW_ROLES)
                        .isDisplayed(true).url("/roles").priority(2).module(adminModule).build();

                Permission p3 = Permission.builder().name("Module Management").code(PermissionConstant.VIEW_MODULES)
                        .isDisplayed(true).url("/modules").priority(3).module(adminModule).build();

                Permission p7 = Permission.builder().name("Flow Management").code(PermissionConstant.VIEW_FLOWS)
                        .url("/process-flows").isDisplayed(true).priority(4).module(adminModule).build();

                Permission p10 = Permission.builder().name("Department Management").code(PermissionConstant.VIEW_DEPARTMENTS)
                        .isDisplayed(true).priority(5).module(adminModule).url("/departments").build();

                Permission p8 = Permission.builder().name("Step Flow Management").code(PermissionConstant.VIEW_STEPS)
                        .isDisplayed(false).module(adminModule).build();

                Permission p9 = Permission.builder().name("Status Flow Management").code(PermissionConstant.VIEW_STATUSES)
                        .isDisplayed(false).module(adminModule).build();

                Permission p4 = Permission.builder().name("Permission Management").code(PermissionConstant.VIEW_PERMISSIONS)
                        .isDisplayed(false).module(adminModule).build();


                Module officeModule = Module.builder().name("Document Management").code("DOC_MGMT").isDisplayed(true).build();

                Permission p5 = Permission.builder().name("Outgoing docs").code(PermissionConstant.VIEW_OUT_GOING_DOCS)
                        .isDisplayed(true).url("/out-going-docs").priority(1).module(officeModule).build();

                Permission p6 = Permission.builder().name("Incoming docs").code(PermissionConstant.VIEW_IN_COMING_DOCS)
                        .isDisplayed(true).url("/in-coming-docs").priority(2).module(officeModule).build();

                // 3. Tạo Roles
                Role admin = Role.builder()
                        .name("Admin")
                        .code(RoleConstant.ADMIN)
                        .permissions(List.of(p1, p2, p3, p4, p7, p8, p9, p10))
                        .build();

                Role director = Role.builder()
                        .name("Director")
                        .code(RoleConstant.DIRECTOR)
                        .permissions(List.of(p5, p6))
                        .build();
                Role manager = Role.builder()
                        .name("Manager")
                        .code(RoleConstant.MANAGER)
                        .permissions(List.of(p5, p6))
                        .build();
                Role specialist = Role.builder()
                        .name("Specialist")
                        .code(RoleConstant.SPECIALIST)
                        .permissions(List.of(p5, p6))
                        .build();
                Role vice_director = Role.builder()
                        .name("Vice Director")
                        .code(RoleConstant.VICE_DIRECTOR)
                        .permissions(List.of(p5, p6))
                        .build();

                // 4. Lưu dữ liệu
                moduleRepository.saveAll(List.of(adminModule, officeModule));
                permissionRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
                roleRepository.saveAll(List.of(admin, director, vice_director, manager, specialist));

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

                Department dep4 = Department.builder()
                        .name("Marketing")
                        .priority(4)
                        .build();

                Department dep5 = Department.builder()
                        .name("Sales")
                        .priority(5)
                        .build();

                Department dep6 = Department.builder()
                        .name("Research and Development")
                        .priority(6)
                        .build();

                Department dep7 = Department.builder()
                        .name("Customer Service")
                        .priority(7)
                        .build();
                departmentRepository.saveAll(List.of(dep1, dep2, dep3, dep4, dep5, dep6, dep7));
                System.out.println("Default departments đã được khởi tạo.");

            }

            if (processFlowRepository.count() == 0) {

            }
        };
    }


}

