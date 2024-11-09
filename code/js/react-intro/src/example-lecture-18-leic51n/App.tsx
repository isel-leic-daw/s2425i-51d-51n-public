import * as React from 'react';
import { CounterComponent } from './CounterComponent';
import { StopWatch } from './StopWatch';

export function App({ title }: { title: number }) {
  const firstKey = title % 2 === 0 ? '0' : '0';
  const secondKey = title % 2 === 0 ? '1' : '1';
  return (
    <div>
      <StopWatch title="first" />
      <StopWatch title="second" />
    </div>
  );
}
