import * as React from 'react';
import { useEffect, useState } from 'react';

type TimerProps = {
  title: string;
  period: number;
};

export function Timer({ title, period }: TimerProps) {
  console.log("render")  
  const [observedValue, setValue] = useState(0);
  const [observedIsRunning, setIsRunning] = useState(true);
  useEffect(() => {
    if(!observedIsRunning) {
        return
    }
    console.log("setInterval")
    const intervalId = setInterval(() => {
      setValue(currValue => currValue + 1);
    }, period * 1000);
    return () => {
        console.log("clearInterval")
        clearInterval(intervalId)
    }
  }, [period, observedIsRunning]);
  function handleClick() {
    setIsRunning(!observedIsRunning)
  }
  const buttonText = observedIsRunning ? "stop" : "start"
  return(
    <div>
        <h2>{title}</h2>
        <p>{observedValue}</p>
        <p>Period is {period}</p>
        <button onClick={handleClick}>{buttonText}</button>
    </div>
  )
}
