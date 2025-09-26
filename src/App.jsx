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
import { MainProvider } from './contexts/MainContext';
import permissionCodes from './constants/permissionCodes';
import Layout from './pages/Layout';
import RoleManagement from './pages/RoleManagement';

function App() {

  const location = useLocation();

  useEffect(() => {
    document.querySelector('html').style.scrollBehavior = 'auto'
    window.scroll({ top: 0 })
    document.querySelector('html').style.scrollBehavior = ''
  }, [location.pathname]); // triggered on route change

  return (
    <MainProvider>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Layout />}>
          <Route
            path="/roles"
            element={
              <PrivateRoute permissionCode={permissionCodes.VIEW_ROLES}>
                <RoleManagement />
              </PrivateRoute>
            }
          />
          <Route
            path="/"
            element={
              <PrivateRoute>
                <Dashboard />
              </PrivateRoute>

            }
          />
        </Route>

        <Route path="/403" element={<Forbidden />} />
      </Routes>
    </MainProvider>
  );
}

export default App;
