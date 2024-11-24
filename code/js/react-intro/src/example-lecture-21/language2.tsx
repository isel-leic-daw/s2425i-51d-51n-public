import React, { useContext, useEffect, useState } from 'react';
import { createContext } from 'react';

type ContextType = {
  browserLanguage: string;
  overrideLanguage: string | undefined;
  setOverrideLanguage: (lang: string | undefined) => void;
};

const defaultState = {
  browserLanguage: navigator.language,
  overrideLanguage: undefined,
  setOverrideLanguage: _ => {},
};

const Context = createContext<ContextType>(defaultState);

export function LanguageProvider({ children }) {
  const [browserLanguage, setBrowserLanguage] = useState(navigator.language);
  const [overrideLanguage, setOverrideLanguage] = useState(undefined);
  useEffect(() => {
    function handler() {
      setBrowserLanguage(navigator.language);
    }
    addEventListener('languagechange', handler);
    return () => {
      removeEventListener('languagechange', handler);
    };
  });
  const value = {
    browserLanguage: browserLanguage,
    overrideLanguage: overrideLanguage,
    setOverrideLanguage: setOverrideLanguage,
  };
  return <Context.Provider value={value}>{children}</Context.Provider>;
}

export function useLanguage():[string, (l: string | undefined) => void] {
  const contextState = useContext(Context);
  return [contextState.overrideLanguage || contextState.browserLanguage, contextState.setOverrideLanguage];
}

/*
<LanguageProvider>
    <p>...
    <div>...
</LanguageProvider>
*/
