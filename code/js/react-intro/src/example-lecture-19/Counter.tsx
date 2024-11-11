import * as React from 'react';
import { useState } from 'react';

type CounterProps = {
  title: string;
  onChange?: (newValue: number) => void;
};

export function Counter({ title, onChange }: CounterProps) {
  const [observedCounter, setCounter] = useState(1);
  function incHandler() {
    setCounter(observedCounter + 1);
    if (onChange) {
      onChange(observedCounter + 1);
    }
  }
  function decHandler() {
    if (observedCounter > 1) {
      setCounter(observedCounter - 1);
      if (onChange) {
        onChange(observedCounter - 1);
      }
    }
  }
  return (
    <div>
      <h2>{title}</h2>
      <h3>{observedCounter}</h3>
      <button onClick={incHandler}>+</button>
      <button onClick={decHandler}>-</button>
    </div>
  );
}
