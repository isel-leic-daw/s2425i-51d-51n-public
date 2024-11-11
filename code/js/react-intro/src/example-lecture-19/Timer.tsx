import * as React from 'react';
import { useEffect, useState } from 'react';

type TimerProps = {
  title: string;
  period: number;
};

export function Timer({ title, period }: TimerProps) {
  const [observedValue, setValue] = useState(0);
  const [observedIsRunning, setIsRunning] = useState(true);
  useEffect(() => {
    // the effect function
    console.log('setInterval');
    const intervalId = setInterval(() => {
      if (observedIsRunning) {
        setValue(currValue => currValue + 1);
      }
    }, period * 1000);
    return () => {
      console.log('clearInterval');
      clearInterval(intervalId);
    };
  }, [period, observedIsRunning]);
  function handleResume() {
    setIsRunning(true);
  }
  function handlePause() {
    setIsRunning(false);
  }
  const resumeIsEnabled: boolean = observedIsRunning == false;
  const pauseIsEnabled: boolean = observedIsRunning == true;
  return (
    <div>
      <h2>{title}</h2>
      <h3>{observedValue}</h3>
      <button disabled={!resumeIsEnabled} onClick={handleResume}>
        resume
      </button>
      <button disabled={!pauseIsEnabled} onClick={handlePause}>
        pause
      </button>
      <p>period: {period}</p>
    </div>
  );
}
