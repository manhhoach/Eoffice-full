import { createContext, useState } from "react";

export const MainContext = createContext();

export const MainProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [permissionCodes, setPermissionCodes] = useState([]);
  const [modules, setModules] = useState([]);
  const [sidebarOpen, setSidebarOpen] = useState(false);
  
  return (
    <MainContext.Provider
      value={{
        user,
        permissionCodes,
        setUser,
        setPermissionCodes,
        modules,
        setModules,
        sidebarOpen,
        setSidebarOpen
      }}
    >
      {children}
    </MainContext.Provider>
  );
}