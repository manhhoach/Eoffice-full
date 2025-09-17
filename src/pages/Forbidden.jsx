import React from 'react';
import { Link } from 'react-router-dom';

function Forbidden() {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 font-inter">
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md text-center">
        <h1 className="text-5xl font-bold text-red-500 mb-4">403</h1>
        <h2 className="text-2xl font-semibold text-gray-800 mb-2">Access Denied</h2>
        <p className="text-gray-600 mb-6">
          You do not have permission to access this page.
        </p>
        <Link
          to="/login"
          className="inline-block bg-violet-600 hover:bg-violet-700 text-white font-semibold py-2 px-6 rounded transition"
        >
          Back to Login
        </Link>
      </div>
    </div>
  );
}

export default Forbidden;