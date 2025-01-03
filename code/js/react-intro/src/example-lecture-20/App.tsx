import * as React from 'react';
import { StopWatch } from './StopWatch';
import { useState } from 'react';
import { Fetch } from './Fetch';

export function App2() {
  const [mount, setMount] = useState(true);
  function clickHandler() {
    setMount(it => !it)
  }
  return <div>
    <button onClick={clickHandler}>toggle</button>
    {mount && <StopWatch title="the stopwatch" />}
  </div>;
}

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
      <p>{text.length}</p>
      <input type="submit" value="submit" onClick={submitHandler} />
      <p>{url}</p>
      <Fetch url={url} />
    </div>
  );
}
