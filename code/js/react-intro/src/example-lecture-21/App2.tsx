import React, { useState } from 'react';
import { useFetch } from './useFetch';
import { LanguageProvider, useLanguage } from './language';

function SomeInnerComponent() {
  const [language] = useLanguage();
  return <p>language is '{language}'</p>;
}
function AnotherComponent() {
  const [language, setLanguage] = useLanguage();
  const [text, setText] = useState('');
  const submitHandler = () => {
    console.log('submitHandler');
    if (text) {
      setLanguage(text);
    } else {
      setLanguage(undefined);
    }
  };
  const changeHandler: React.ChangeEventHandler<HTMLInputElement> = ev => {
    setText(ev.target.value);
  };
  return (
    <div>
      <h2>Override language</h2>
      <input type="text" value={text} onChange={changeHandler} style={{ width: '50%' }} />
      <input type="submit" value="submit" onClick={submitHandler} />
    </div>
  );
}

export function App() {
  const [text, setText] = useState('');
  const [url, setUrl] = useState<string | undefined>(undefined);
  const fetchState = useFetch(url);

  const submitHandler = () => {
    console.log('submitHandler');
    setUrl(text);
  };
  const changeHandler: React.ChangeEventHandler<HTMLInputElement> = ev => {
    setText(ev.target.value);
  };
  return (
    <LanguageProvider>
      <div>
        <SomeInnerComponent />
        <AnotherComponent />
        <input type="text" value={text} onChange={changeHandler} style={{ width: '50%' }} />
        <input type="submit" value="submit" onClick={submitHandler} />
        <p>{url}</p>
        {fetchState.type === 'begin' && <p>starting...</p>}
        {fetchState.type === 'loading' && <p>loading...</p>}
        {fetchState.type === 'loaded' && <pre>{JSON.stringify(fetchState.payload)}</pre>}
        {fetchState.type === 'error' && <pre>{fetchState.error.message}</pre>}
      </div>
    </LanguageProvider>
  );
}
