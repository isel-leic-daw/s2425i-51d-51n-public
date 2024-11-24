import React from 'react'
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { Home } from "./Home";
import { Page1 } from './Page1';

const router = createBrowserRouter([
    {
        path: "/",
        element: <Home />,
    },
    {
        path: "/page-1",
        element: <Page1 />,
    }
])

export function App() {
    return <RouterProvider router={router} />
}