import React from 'react';
import { Link } from 'react-router-dom';

export function Home() {
  return (
    <div>
      <h2>Home</h2>
      <Link to="/page-1">Page 1</Link>
    </div>
  );
}
