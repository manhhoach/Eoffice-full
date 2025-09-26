import React, { useState } from "react";
import { NavLink } from "react-router-dom";

function SidebarModule({ module }) {
   const [isOpen, setIsOpen] = useState(false);

   return (
      <li className="mb-2">
         {/* Header module */}
         <button
            onClick={() => setIsOpen(!isOpen)}
            className="flex items-center justify-between w-full px-3 py-2 rounded-lg text-gray-700 dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-700 transition"
         >
            <span className="text-sm font-semibold">{module.name}</span>
            <svg
               className={`w-3 h-3 transition-transform ${isOpen ? "rotate-180" : ""
                  }`}
               viewBox="0 0 12 12"
            >
               <path
                  fill="currentColor"
                  d="M5.9 11.4L.5 6l1.4-1.4 4 4 4-4L11.3 6z"
               />
            </svg>
         </button>

         {/* Permissions */}
         {isOpen && (
            <ul className="mt-1">
               {module.permissions
                  ?.filter((p) => p.isDisplayed)
                  .map((perm) => (
                     <li key={perm.id}>
                        <NavLink
                           to={perm.url}
                           className={({ isActive }) =>
                              `block pl-6 pr-3 py-2 rounded-lg text-sm font-medium transition ${isActive
                                 ? "bg-violet-100 text-violet-600 dark:bg-violet-600/20 dark:text-violet-200"
                                 : "text-gray-700 hover:text-gray-900 dark:text-gray-200 dark:hover:text-white"
                              }`
                           }
                        >
                           {perm.name}
                        </NavLink>
                     </li>
                  ))}
            </ul>
         )}
      </li>
   );
}

export default SidebarModule;
