import * as React from 'react';
import { useState } from 'react';

type CounterComponentProps = {
  title: string;
};

export function CounterComponent({ title }: CounterComponentProps) {
  const [counter, setCounter] = useState<number>(0);
  
  function upHandler() {
    setCounter(counter + 1);
  }
  function downHandler() {
    setCounter(counter - 1);
  }
  return (
    <div>
      <h2>{title}</h2>
      <p>{counter}</p>
      <button onClick={upHandler}>up</button>
      <button onClick={downHandler}>down</button>
      <input type="text" />
    </div>
  );
}
