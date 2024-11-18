import * as React from 'react';
import { useState } from 'react';
import { SomeComponentUsingFetch } from './SomeComponentUsingFetch';

export function App() {
  const [text, setText] = useState('');
  const [url, setUrl] = useState<string | undefined>(undefined);
  const submitHandler = () => {
    console.log('submitHandler');
    setUrl(text);
  };
  const changeHandler: React.ChangeEventHandler<HTMLInputElement> = ev => {
    setText(ev.target.value);
  };

  return (
    <div>
      <input type="text" value={text} onChange={changeHandler} style={{ width: '50%' }} />
      <input type="submit" value="submit" onClick={submitHandler} />
      <p>{url}</p>
      <SomeComponentUsingFetch url={url} />
    </div>
  );
}
