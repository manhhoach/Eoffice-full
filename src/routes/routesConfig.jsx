import permissionCodes from "../constants/permissionCodes";
import RoleManagement from "../pages/RoleManagement";
import ModuleManagement from "../pages/ModuleManagement";
import PermissionManagement from "../pages/PermissionManagement";
import UserManagement from "../pages/UserManagement";
import ProcessFlowManagement from "../pages/ProcessFlowManagement";
import Dashboard from "../pages/Dashboard";
import DepartmentManagement from './../pages/DepartmentManagement'
import ProcessStatusManagement from './../pages/ProcessStatusManagement'
import ProcessStepManagement from './../pages/ProcessStepManagement'

const appRoutes = [
   { path: "/roles", component: RoleManagement, permission: permissionCodes.VIEW_ROLES },
   { path: "/modules", component: ModuleManagement, permission: permissionCodes.VIEW_MODULES },
   { path: "/modules/:id/permissions", component: PermissionManagement, permission: permissionCodes.VIEW_PERMISSIONS },
   { path: "/users", component: UserManagement, permission: permissionCodes.VIEW_USERS },
   { path: "/process-flows", component: ProcessFlowManagement, permission: permissionCodes.VIEW_FLOWS },
   { path: "/process-flows/:id/statuses", component: ProcessStatusManagement, permission: permissionCodes.VIEW_STATUSES },
   { path: "/process-flows/:id/steps", component: ProcessStepManagement, permission: permissionCodes.VIEW_STEPS },
   { path: "/departments", component: DepartmentManagement, permission: permissionCodes.VIEW_DEPARTMENTS },
   { path: "/", component: Dashboard },
];

export default appRoutes