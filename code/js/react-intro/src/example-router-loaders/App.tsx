import * as React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { Home } from './Home';
import { Panel1 } from './Panel1';
import { Panel2 } from './Panel2';
import {load1, load2} from './loaders'

const router = createBrowserRouter([
  {
    path: '/',
    element: (
        <Home />
    ),
    children: [
        {
            path: "/",
            element: (<h2>Nothing</h2>)
        },
        {
            path: "/panel-1",
            element: (
                <Panel1 />
            ),
            loader: ({request}) => {
                return load1()
            }
        },
        {
            path: "/panel-2",
            element: (
                <Panel2 />
            ),
            loader: ({request}) => {
                return load2()
            }
        }
    ]
  },
]);

export function App() {
    return <RouterProvider router={router} />
}
