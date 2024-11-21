import React, { useContext } from 'react';
import { createContext, useState } from 'react';
import { Navigate, useLocation } from 'react-router-dom';

type State = {
  username: string | undefined;
  setUsername: (username: string | undefined) => void;
};

const AuthenticationContext = createContext({ username: undefined, setUsername: _ => {} });

export function AuthenticationProvider({ children }) {
  const [observedUsername, setUsername] = useState(undefined);
  console.log(`provider ${observedUsername}`);
  const value = {
    username: observedUsername,
    setUsername: setUsername,
  };
  return <AuthenticationContext.Provider value={value}>{children}</AuthenticationContext.Provider>;
}

export function useAuthentication() {
  const state = useContext(AuthenticationContext);

  return [
    state.username,
    username => {
      console.log(`setUsername: ${username}`);
      state.setUsername(username);
    },
  ];
}

