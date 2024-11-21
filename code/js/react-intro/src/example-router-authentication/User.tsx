import React from 'react';
import { useAuthentication } from './authentication';
import { Link } from 'react-router-dom';

export function User() {
  const [username, setUsername] = useAuthentication();
  function handler() {
    setUsername(undefined)
  }
  if (username) {
    return <p>{username} <button onClick={handler}>logout</button></p>;
  } else {
    return <Link to="/login">login</Link>;
  }
}
