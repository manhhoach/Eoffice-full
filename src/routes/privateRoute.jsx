// src/components/PrivateRoute.jsx
import { Navigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";

export default function PrivateRoute({ permissionCode, children }) {
    const { user, permissions } = useContext(AuthContext);

    // chưa login → quay về /login
    if (!user) return <Navigate to="/login" replace />;

    // không có quyền → sang /403
    const hasPermission = permissions.some(p => p.code === permissionCode);
    if (!hasPermission) return <Navigate to="/403" replace />;

    // có quyền → render trang
    return children;
}
