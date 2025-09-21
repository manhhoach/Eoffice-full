import React, { useEffect } from 'react';
import {
  Routes,
  Route,
  useLocation
} from 'react-router-dom';

import './css/style.css';

import './charts/ChartjsConfig';

// Import pages
import Dashboard from './pages/Dashboard';
import Login from './pages/Login';
import Forbidden from './pages/Forbidden';
import PrivateRoute from './routes/privateRoute';
import { AuthProvider } from './contexts/AuthContext';
import permissionCodes from './constants/permissionCodes';

function App() {

  const location = useLocation();

  useEffect(() => {
    document.querySelector('html').style.scrollBehavior = 'auto'
    window.scroll({ top: 0 })
    document.querySelector('html').style.scrollBehavior = ''
  }, [location.pathname]); // triggered on route change

  return (
    <AuthProvider>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route
          path="/"
          element={
            <PrivateRoute permissionCode={permissionCodes.VIEW_DASHBOARD}>
              <Dashboard />
            </PrivateRoute>
          }
        />
        <Route path="/403" element={<Forbidden />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
