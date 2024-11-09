import * as React from 'react';
import { Counter } from './Counter';

export function App({ replicas }: { replicas: number }) {
  let counter = 0;
  const elements = [];
  while (counter < replicas) {
    elements.push(<Counter title={counter.toString()} key={counter} />);
    counter += 1
  }
  return <div>{elements}</div>;
}
