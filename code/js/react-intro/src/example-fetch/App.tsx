import * as React from 'react';
import { Fetch } from './Fetch';
import { useState } from 'react';

export function App() {
  const [text, setText] = useState('');
  const [uri, setUri] = useState('');
  const handleChange: React.ChangeEventHandler<HTMLInputElement> = ev => {
    setText(ev.target.value);
  };
  const handleSubmit = () => {
    console.log(`setting uri with ${text}`);
    setUri(text);
  };
  return (
    <div>
      <input type="text" value={text} onChange={handleChange} onSubmit={handleSubmit} style={{width: "50%"}}/>
      <button onClick={handleSubmit}>submit</button>
      <Fetch uri={uri} />
    </div>
  );
}
