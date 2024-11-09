import * as React from 'react';
import { useState, useEffect } from 'react';
type CounterProps = {
  title: string;
};
export function StopWatch({ title }: CounterProps) {
  // state
  const [observedCounter, setCounter] = useState(0);
  const [observedIsRunning, setIsRunning] = useState(true);
  //
  useEffect(() => {
    if(!observedIsRunning) {
        return
    }
    console.log('calling setInterval');
    const intervalId = setInterval(() => {
      console.log('setInterval callback');
      setCounter(currCounter => currCounter + 1);
    }, 1000);
    return () => {
      clearInterval(intervalId);
    };
  }, [observedIsRunning]);
  //
  const startEnabled = !observedIsRunning;
  const stopEnabled = observedIsRunning;
  function handleStartClick() {
    setIsRunning(true);
  }
  function handleStopClick() {
    setIsRunning(false);
  }
  return (
    <div>
      <p>IsRunning: {observedIsRunning.toString()}</p>
      <p>{observedCounter}</p>
      <button disabled={!startEnabled} onClick={handleStartClick}>
        Start
      </button>
      <button disabled={!stopEnabled} onClick={handleStopClick}>
        Stop
      </button>
    </div>
  );
}
