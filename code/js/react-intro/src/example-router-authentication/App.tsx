import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { Home } from './Home';
import { Page1 } from './Page1';
import { Page2 } from './Page2';
import { Layout } from './Layout';
import { Login } from './Login';
import { AuthenticationProvider } from './authentication';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      {
        path: '/',
        element: <Home />,
      },
      {
        path: '/login',
        element: <Login />,
      },
      {
        path: '/page-1',
        element: <Page1 />,
      },
      {
        path: '/page-2',
        element: <Page2 />,
      },
    ],
  },
]);

export function App() {
  return (
    <AuthenticationProvider>
      <RouterProvider router={router} />
    </AuthenticationProvider>
  );
}
