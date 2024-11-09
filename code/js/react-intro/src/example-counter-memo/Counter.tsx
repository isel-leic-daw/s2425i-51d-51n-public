import * as React from 'react';
import { useState, memo } from 'react';

type CounterProps = {
  label: string;
  onChange: (newValue: number) => void;
};
export const Counter = memo(function Counter({ label, onChange }: CounterProps) {
  const [observedCounter, setCounter] = useState(0);
  console.log(`Counter with label=${label}`);
  return (
    <div>
      <h2>{label}</h2>
      <p>{observedCounter}</p>
      <button
        onClick={() => {
          setCounter(observedCounter + 1);
          onChange(observedCounter + 1);
        }}
      >
        +
      </button>
      <button
        onClick={() => {
          setCounter(observedCounter - 1);
          onChange(observedCounter - 1);
        }}
      >
        -
      </button>
    </div>
  );
});
