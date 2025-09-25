import { Outlet } from "react-router-dom";
import Sidebar from '../partials/Sidebar';
import Header from '../partials/Header';

export default function Layout() {
   return (
      <div className="flex h-screen overflow-hidden">
         <Sidebar />
         <div className="relative flex flex-col flex-1 overflow-y-auto overflow-x-hidden">
            <Header />
            <main className="grow">
               <Outlet />
            </main>
         </div>
      </div>
   );
}