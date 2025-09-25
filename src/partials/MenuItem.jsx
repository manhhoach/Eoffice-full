import React from "react";

export default function MenuItem() {
   return (
      <React.Fragment>
         <a
            href="#0"
            className={`block text-gray-800 dark:text-gray-100 truncate transition duration-150 ${pathname.includes("tasks") ? "" : "hover:text-gray-900 dark:hover:text-white"
               }`}
            onClick={(e) => {
               e.preventDefault();
               handleClick();
               setSidebarExpanded(true);
            }}
         >
            <div className="flex items-center justify-between">
               <div className="flex items-center">
                  <span className="text-sm font-medium ml-4 lg:opacity-0 lg:sidebar-expanded:opacity-100 2xl:opacity-100 duration-200">
                     Tasks
                  </span>
               </div>
               {/* Icon */}
               <div className="flex shrink-0 ml-2">
                  <svg className={`w-3 h-3 shrink-0 ml-1 fill-current text-gray-400 dark:text-gray-500 ${open && "rotate-180"}`} viewBox="0 0 12 12">
                     <path d="M5.9 11.4L.5 6l1.4-1.4 4 4 4-4L11.3 6z" />
                  </svg>
               </div>
            </div>
         </a>
         <div className="lg:hidden lg:sidebar-expanded:block 2xl:block">
            <ul className={`pl-8 mt-1 ${!open && "hidden"}`}>
               <li className="mb-1 last:mb-0">
                  <NavLink
                     end
                     to="https://cruip.com/mosaic/"
                     className={({ isActive }) =>
                        "block transition duration-150 truncate " + (isActive ? "text-violet-500" : "text-gray-500/90 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200")
                     }
                  >
                     <span className="text-sm font-medium lg:opacity-0 lg:sidebar-expanded:opacity-100 2xl:opacity-100 duration-200">
                        Kanban
                     </span>
                  </NavLink>
               </li>
            </ul>
         </div>
      </React.Fragment>
   );
}