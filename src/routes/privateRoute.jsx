// src/components/PrivateRoute.jsx
import { Navigate } from "react-router-dom";
import { useContext } from "react";
import { MainContext } from "../contexts/MainContext";

export default function PrivateRoute({ permissionCode, children }) {
    const { user, permissionCodes } = useContext(MainContext);
    if (!user) return <Navigate to="/login" replace />;

    const hasPermission = permissionCodes.some(p => p === permissionCode);
    if (!hasPermission) return <Navigate to="/403" replace />;

    return children;
}
