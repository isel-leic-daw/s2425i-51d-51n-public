import React from 'react';
import { Link, Outlet } from 'react-router-dom';
import { User } from './User';

export function Layout() {
  return (
    <>
      <h2>Layout</h2>
      <User />
      <ul>
        <li><Link to="/">Home</Link></li>
        <li><Link to="/page-1">Page 1</Link></li>
        <li><Link to="/page-2">Page 2</Link></li>
      </ul>
      <Outlet />
    </>
  );
}
