// src/components/PrivateRoute.jsx
import { Navigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";

export default function PrivateRoute({ permissionCode, children }) {
    const { user, permissionCodes } = useContext(AuthContext);
    console.log(user, permissionCodes);
    if (!user) return <Navigate to="/login" replace />;


    const hasPermission = permissionCodes.some(p => p === permissionCode);
    if (!hasPermission) return <Navigate to="/403" replace />;


    return children;
}
