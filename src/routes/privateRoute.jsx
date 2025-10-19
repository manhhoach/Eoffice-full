import { Navigate } from "react-router-dom";
import { useContext, useEffect } from "react";
import { MainContext } from "../contexts/MainContext";
import useApi from "../hooks/useApi";
import { useNavigate } from "react-router-dom";

export default function PrivateRoute({ permissionCode, children }) {
    const { user, permissionCodes, setUser, SetPermissionForRoles, setPermissionCodes } = useContext(MainContext);
    let accessToken = localStorage.getItem("accessToken");
    const navigate = useNavigate()
    const { data, refetch, error } = useApi({
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
        if (!data) return;
        if (data.success) {
            setUser(data.data.username)
            SetPermissionForRoles(data.data.modules)
            setPermissionCodes(data.data.permissionCodes)
        } else {
            localStorage.removeItem("accessToken")
            setUser(null)
            SetPermissionForRoles([])
            setPermissionCodes([])
            return navigate("/login", { replace: true })
        }
    }, [data]);

    if (!accessToken || error) return <Navigate to="/login" replace />;
    if (!user && !data && !error) return null;
    const hasPermission = permissionCodes.some(p => p === permissionCode) || !permissionCode;
    if (!hasPermission) return <Navigate to="/403" replace />;

    return children;
}
