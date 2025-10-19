import { createContext, useState } from "react";

export const MainContext = createContext();

export const MainProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [permissionCodes, setPermissionCodes] = useState([]);
  const [modules, SetPermissionForRoles] = useState([]);
  const [sidebarOpen, setSidebarOpen] = useState(false);

  return (
    <MainContext.Provider
      value={{
        user,
        permissionCodes,
        setUser,
        setPermissionCodes,
        modules,
        SetPermissionForRoles,
        sidebarOpen,
        setSidebarOpen
      }}
    >
      {children}
    </MainContext.Provider>
  );
}