import { createContext, useState, useEffect } from "react";
import api from "../api/axios";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [permissions, setPermissions] = useState([]);
  const [token, setToken] = useState(localStorage.getItem("token") || null);

  
  return (
    <AuthContext.Provider
      value={{
        user,
        permissions,
        token,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}