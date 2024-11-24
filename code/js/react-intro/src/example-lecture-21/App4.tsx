import React, { useState } from 'react';
import { useFetch } from './useFetch2';
import { LanguageProvider, useLanguage } from './language2';

function SomeComponent({ url }: { url: string }) {
  const fetchState = useFetch(url);
  return (
    <div>
      {fetchState.type === 'loaded' && (
        <textarea cols={80} rows={40} readOnly value={JSON.stringify(JSON.parse(fetchState.payload), null, 2)} />
      )}
      {fetchState.type === 'begin' && <p>Idle</p>}
      {fetchState.type === 'loading' && <p>Loading...</p>}
      {fetchState.type === 'error' && <p>{fetchState.error.message}</p>}
    </div>
  );
}

export function App2() {
  return (
    <div>
      <h2>useFetch example</h2>
      <SomeComponent url="https://httpbin.org/delay/3" />
    </div>
  );
}

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
  return (
    <LanguageProvider>
      <SomeInnerComponent />
      <AnotherComponent />
    </LanguageProvider>
  );
}
