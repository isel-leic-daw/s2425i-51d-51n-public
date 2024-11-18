import React from 'react';
import { LanguageProvider, useLanguage } from './language';

export function SomeComponent() {
  const language = useLanguage();
  return <p>Language is {language}</p>;
}

export function App() {
  return (
    <LanguageProvider>
      <SomeComponent />
    </LanguageProvider>
  );
}
