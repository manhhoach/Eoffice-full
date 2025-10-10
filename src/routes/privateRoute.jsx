import { Navigate } from "react-router-dom";
import { useContext, useEffect } from "react";
import { MainContext } from "../contexts/MainContext";
import useApi from "../hooks/useApi";

export default function PrivateRoute({ permissionCode, children }) {
    const { user, permissionCodes, setUser, setModules, setPermissionCodes } = useContext(MainContext);
    let accessToken = localStorage.getItem("accessToken");

    const { data, refetch } = useApi({
        url: 'auth/me',
        headers: {
            Authorization: `Bearer ${accessToken}`,
        },
        auto: false
    })
    useEffect(() => {
        if (!user && accessToken) {
            refetch(); // gọi API
        }
    }, [user, accessToken]);

    useEffect(() => {
        if (data && data.success) {
            setUser(data.data.username)
            setModules(data.data.modules)
            setPermissionCodes(data.data.permissionCodes)
        }

    }, [data]);

    if (!accessToken) return <Navigate to="/login" replace />;
    const hasPermission = permissionCodes.some(p => p === permissionCode) || !permissionCode;
    if (!hasPermission) return <Navigate to="/403" replace />;

    return children;
}
