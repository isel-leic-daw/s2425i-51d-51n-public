import * as React from 'react';
import { useEffect, useRef, useState } from 'react';

type StopWatchProps = {
  title: string;
};
export function StopWatch({ title }: StopWatchProps) {
  // --
  const [observedTime, setTime] = useState(0);
  const [observedIsRunning, setIsRunning] = useState(true);
  const refToCurrentIsRunning = useRef(true)
  // --
  useEffect(
    // the effect
    () => {
      console.log("Running the effect, i.e., calling setInterval")  
      const intervalId = setInterval(
        // the timer callback
        () => {
          console.log(`setInterval callback with observedTime=${observedTime}`)  
          if(refToCurrentIsRunning.current) {
            setTime(it => it + 1);
          }
        },
        1000
      );
      // cleanup function returned by the effect
      return () => {
        clearInterval(intervalId)
      }
    },
    [] // TODO explain this!
  );
  // --
  const startEnabled = !observedIsRunning;
  const stopEnabled = observedIsRunning;
  // --
  function handleStart() {
    setIsRunning(true);
    refToCurrentIsRunning.current = true
  }
  function handleStop() {
    setIsRunning(false);
    refToCurrentIsRunning.current = false
  }
  // --
  return (
    <div>
      <h2>{title}</h2>
      <p>{observedTime}</p>
      <button disabled={!startEnabled} onClick={handleStart}>
        start
      </button>
      <button disabled={!stopEnabled} onClick={handleStop}>
        stop
      </button>
    </div>
  );
}
