import React from 'react';
import { useAuthentication } from './authentication';
import { Navigate, useLocation } from 'react-router-dom';

export function RequireAuthentication({ children }) {
  const [username] = useAuthentication(); // from our own context
  const location = useLocation(); // from React Router
  console.log(`RequireAuthentication: ${location.pathname}`);
  if (username) {
    return children;
  } else {
    return <Navigate to="/login" state={{ source: location.pathname }} replace={true} />;
  }
}
