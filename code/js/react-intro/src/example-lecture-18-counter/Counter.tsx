import * as React from 'react';
import { useState } from 'react';
type CounterProps = {
  title: string;
};
export function Counter({title}: CounterProps) {
    const [observedCounter, setCounter] = useState(0)
    function incHandler() {
        setCounter(observedCounter + 1)
    }
    function decHandler() {
        setCounter(observedCounter - 1)
    }
    return <div>
        <p>{title}</p>
        <button onClick={incHandler}>+</button>
        <span>{observedCounter}</span>
        <button onClick={decHandler}>-</button>
    </div>

}
