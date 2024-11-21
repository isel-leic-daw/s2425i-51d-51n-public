import React from 'react';
import { useAuthentication } from './authentication';
import { RequireAuthentication } from './RequireAuthentication';

export function Page2() {
  const [username] = useAuthentication();
  return (
    <RequireAuthentication>
      <h2>Page 2</h2>
      <p>Hello {username}</p>
    </RequireAuthentication>
  );
}
