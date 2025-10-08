import React, { useEffect } from 'react';
import {
  Routes,
  Route,
  useLocation
} from 'react-router-dom';
import './css/style.css';
import './charts/ChartjsConfig';

import PrivateRoute from './routes/privateRoute'
import Login from './pages/Login';
import Forbidden from './pages/Forbidden';
import { MainProvider } from './contexts/MainContext';
import Layout from './pages/Layout';
import appRoutes from './routes/routesConfig'


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
          {appRoutes.map(({ path, component: Component, permission }) => {
            return <Route
              key={path}
              path={path}
              element={
                <PrivateRoute permissionCode={permission}>
                  <Component />
                </PrivateRoute>
              }
            />
          })}
        </Route>

        <Route path="/403" element={<Forbidden />} />
      </Routes>
    </MainProvider>
  );
}

export default App;
