import React, { createContext, useContext, useEffect, useState } from 'react';
type LanguageContextType = {
  language: string;
};

const LanguageContext = createContext<LanguageContextType>({ language: 'en' });

export function useLanguage() {
    return useContext(LanguageContext).language
}

export function LanguageProvider({ children }) {
  const [state, setState] = useState('en');
  useEffect(() => {
    function handler() {
        setState(navigator.language)
    }
    addEventListener("languagechange", handler)
    return () => {
        removeEventListener("languagechange", handler)
    }
  })
  return <LanguageContext.Provider value={{ language: state }}>{children}</LanguageContext.Provider>;
}
