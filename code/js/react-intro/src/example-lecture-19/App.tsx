import * as React from 'react';
import { Counter } from './Counter';
import { Timer } from './Timer';
import { useState } from 'react';

export function App() {
    const [observedPeriod, setPeriod] = useState(1)
    function handleChange(newValue) {
        setPeriod(newValue)
    }
    return (
        <div>
            <p>Desired period is {observedPeriod}</p>
            <Counter title="The counter" onChange={handleChange}/>
            <Timer title="The timer" period={observedPeriod}/>
        </div>
    )
}