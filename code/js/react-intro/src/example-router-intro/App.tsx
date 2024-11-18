import React from 'react'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import { Home } from './Home'
import { Panel1 } from './Panel1'
import { Panel2 } from './Panel2'
import { Panel3 } from './Panel3'

const router = createBrowserRouter([
    {
        path: "/",
        element: <Home />,
        children: [
            {
                path: "/panel1",
                element: <Panel1 />
            },
            {
                path: "/panel2/:id",
                element: <Panel2 />
            },
            {
                path: "/panel3/:id/path/:id2",
                element: <Panel3 />
            }
        ]
    }
])

export function App() {
    return <RouterProvider router={router} />
}