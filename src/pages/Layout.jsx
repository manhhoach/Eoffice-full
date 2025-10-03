import { Outlet } from "react-router-dom";
import Sidebar from '../partials/Sidebar';
import Header from '../partials/Header';
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

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
         <ToastContainer
            position="top-right"
            autoClose={3000}
            hideProgressBar={false}
            newestOnTop
            closeOnClick
            pauseOnHover
            draggable
            theme="light"
         />
      </div>
   );
}