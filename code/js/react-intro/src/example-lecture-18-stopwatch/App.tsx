import * as React from 'react';
import { StopWatch } from './StopWatch';

export function App({ replicas }: { replicas: number }) {
  let counter = 0;
  const elements = [];
  while (counter < replicas) {
    elements.push(<StopWatch title={counter.toString()} key={counter} />);
    counter += 1
  }
  return <div>{elements}</div>;
}
