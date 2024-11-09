import * as React from 'react';
import { Counter } from './Counter';
import { useCallback, useState } from 'react';

export function App() {
  const [observedCounters, setCounters] = useState([0, 0]);
  const sum = observedCounters[0] + observedCounters[1];
  console.log(`App render`)
  const onChange = [
    useCallback(newValue => setCounters(previous => [previous[0], newValue]), []),
    useCallback(newValue => setCounters(previous => [newValue, previous[1]]), []),
  ];
  return (
    <div>
      <p>Sum is {sum}</p>
      <Counter label="my first counter" onChange={onChange[0]} />
      <Counter label="my second counter" onChange={onChange[1]} />
    </div>
  );
}
