import React, { useContext, useEffect, useState } from 'react';
import { createContext } from 'react';

type State = {
  language: string;
  setLanguage: (string) => void;
};

const LanguageContext = createContext<State>({
  language: 'en',
  setLanguage: () => {},
});

export function LanguageProvider({ children }) {
  const [observedOverride, setOverride] = useState(undefined);
  const [observedLanguage, setLanguage] = useState('en');
  useEffect(() => {
    function handler() {
      setLanguage(navigator.language);
    }
    addEventListener('languagechange', handler);
    return () => {
      removeEventListener('languagechange', handler);
    };
  }, []);
  const value = {
    language: observedOverride || observedLanguage,
    setLanguage: (override: string) => {setOverride(override)}
  }
  return <LanguageContext.Provider value={value}>{children}</LanguageContext.Provider>;
}

export function useLanguage(): [string, (string)=>void] {
  const state = useContext(LanguageContext);
  return [
    state.language,
    state.setLanguage
  ]
}
