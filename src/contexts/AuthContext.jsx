import { createContext, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [permissionCodes, setPermissionCodes] = useState([]);
  const [token, setToken] = useState(localStorage.getItem("token") || null);
  const [modules, setModules] = useState([]);

  return (
    <AuthContext.Provider
      value={{
        user,
        permissionCodes,
        token,
        setUser,
        setPermissionCodes,
        modules,
        setModules
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}