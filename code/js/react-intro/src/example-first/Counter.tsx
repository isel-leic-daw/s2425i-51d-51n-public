import * as React from 'react';
import { useState } from 'react';
import { CounterValue } from './CounterValue';

type CounterProps = {
  title: string;
};
// This is a React component
// A component is a function:
// - From the model that I want to present
// - To the view of how I want to present
export function Counter({ title }: CounterProps): React.ReactElement {
  const [observedValue, setValue] = useState(0);
  const incHandler = () => setValue(observedValue + 1);
  const decHandler = () => setValue(observedValue - 1);
  return (
    <div>
      <h2>{title}</h2>
      <button onClick={incHandler}>+</button>
      <CounterValue value={observedValue} />
      <button onClick={decHandler}>-</button>
    </div>
  );
}
