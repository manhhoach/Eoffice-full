package com.manhhoach.EofficeFull.entity.mappingHolder;

import com.manhhoach.EofficeFull.entity.BaseEntity;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.SqlResultSetMapping;
import org.hibernate.annotations.Subselect;

@Entity
@SqlResultSetMapping(
        name = "PermissionModuleDtoMapping",
        classes = @ConstructorResult(
                targetClass = com.manhhoach.EofficeFull.dto.permission.PermissionModuleDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "url", type = String.class),
                        @ColumnResult(name = "is_displayed", type = Boolean.class),
                        @ColumnResult(name = "priority", type = Integer.class),
                        @ColumnResult(name = "moduleId", type = Long.class),
                        @ColumnResult(name = "moduleName", type = String.class),
                        @ColumnResult(name = "moduleCode", type = String.class),
                        @ColumnResult(name = "moduleIsDisplayed", type = Boolean.class)
                }
        )
)
@Subselect("select 1")
public class MappingPermission extends BaseEntity {
}
