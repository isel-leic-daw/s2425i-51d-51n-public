import * as React from 'react';
import { Link, Outlet, useNavigation } from 'react-router-dom';

export function Home() {
  const navigation = useNavigation()  
  return (
    <div>
      <h1>Home</h1>
      {navigation.state}
      <ul>
      <li><Link to="/panel-1">panel 1</Link></li>
      <li><Link to="/panel-2">panel 2</Link></li>
      </ul>
      <Outlet />
    </div>
  );
}
