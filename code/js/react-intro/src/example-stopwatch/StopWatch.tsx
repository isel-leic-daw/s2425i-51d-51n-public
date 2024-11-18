import * as React from 'react';
import { useEffect, useReducer, useRef } from 'react';

type State = {
  partials: Array<number>;
  startTimestamp: number;
  elapsed: number;
  running: boolean;
};

const initialState: State = {
  partials: [],
  startTimestamp: 0,
  running: false,
  elapsed: 0,
};

type Action =
  | { type: 'start'; timestamp: number }
  | { type: 'tick'; timestamp: number }
  | { type: 'stop' }
  | { type: 'clear' }
  | { type: 'lap'; timestamp: number };

function reducer(state: State, action: Action): State {
  switch (action.type) {
    case 'start':
      return {
        ...state,
        startTimestamp: action.timestamp,
        elapsed: 0,
        running: true,
      };
    case 'stop':
      return { ...state, running: false };
    case 'clear':
      return {
        partials: [],
        startTimestamp: 0,
        elapsed: 0,
        running: false,
      };
    case 'tick':
      return {
        ...state,
        elapsed: action.timestamp - state.startTimestamp,
      };
    case 'lap':
      return {
        ...state,
        partials: [...state.partials, action.timestamp - state.startTimestamp],
      };
  }
}

type StopWatchProps = {
  title: string;
};
export function StopWatch({ title }: StopWatchProps) {
  const [state, dispatch] = useReducer(reducer, initialState);
  const intervalId = useRef(null);
  useEffect(() => {
    return () => {
      if (intervalId.current) {
        clearInterval(intervalId.current);
      }
    };
  }, []);
  const startStopText = state.running ? 'stop' : 'start';
  const clearEnabled = !state.running && state.elapsed != 0;
  const lapEnabled = state.running;

  const startStopHandler = state.running
    ? () => {
        // stop
        clearInterval(intervalId.current);
        intervalId.current = null;
        dispatch({ type: 'stop' });
      }
    : () => {
        // start
        intervalId.current = setInterval(() => {
          console.log('tick')  
          dispatch({ type: 'tick', timestamp: Date.now() });
        }, 100);
        dispatch({ type: 'start', timestamp: Date.now() });
      };
  function lapHandler() {
    dispatch({ type: 'lap', timestamp: Date.now() });
  }
  function clearHandler() {
    dispatch({ type: 'clear' });
  }

  return (
    <div>
      <h2>{title}</h2>
      <p>{state.elapsed}</p>
      <button onClick={startStopHandler}>{startStopText}</button>
      <button disabled={!lapEnabled} onClick={lapHandler}>
        lap
      </button>
      <button disabled={!clearEnabled} onClick={clearHandler}>
        clear
      </button>
      <ul>
        {state.partials.map((it, ix) => (
          <li key={ix}>{it}</li>
        ))}
      </ul>
    </div>
  );
}
