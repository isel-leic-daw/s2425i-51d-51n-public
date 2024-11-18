import * as React from 'react';
import { useEffect, useReducer, useRef } from 'react';

// The State type
type State = {
  partials: Array<number>;
  isRunning: boolean;
  startTimestamp: number;
  tickTimestamp: number;
};

// The Action type
type Action =
  | { type: 'start'; timestamp: number }
  | { type: 'stop' }
  | { type: 'partial'; timestamp: number }
  | { type: 'clear' }
  | { type: 'tick'; timestamp: number };

// The reducer function (State, Action) => State
function reducer(state: State, action: Action): State {
  switch (action.type) {
    case 'start': {
      return {
        ...state,
        isRunning: true,
        startTimestamp: action.timestamp,
        tickTimestamp: action.timestamp,
      };
    }
    case 'stop': {
      return { ...state, isRunning: false };
    }
    case 'partial': {
      return { ...state, partials: [...state.partials, action.timestamp - state.startTimestamp] };
    }
    case 'clear': {
      return { ...state, partials: [] };
    }
    case 'tick': {
      if (state.isRunning) {
        return { ...state, tickTimestamp: action.timestamp };
      } else {
        return state;
      }
    }
  }
}

const initialState: State = {
  partials: [],
  isRunning: false,
  startTimestamp: 0,
  tickTimestamp: 0,
};

function clearTimer(ref: {current?: NodeJS.Timeout}) {
    const iid = ref.current
    if(iid) {
        console.log('clearInterval')
        clearInterval(iid)
        ref.current = undefined
    }
}

type StopWatchProps = {
  title: string;
};
const myRef = {current: undefined}
export function StopWatch({ title }: StopWatchProps) {
  const [state, dispatch] = useReducer(reducer, initialState);
  const intervalIdRef = useRef(undefined);
  useEffect(() => {
    console.log("mount")
    return () => {
      console.log("unmount")  
      clearTimer(intervalIdRef)
    };
  }, []);
  function startStopClickHandler() {
    if (state.isRunning) {
      // stop
      clearTimer(intervalIdRef)
      dispatch({ type: 'stop' });
    } else {
      // start
      clearTimer(intervalIdRef)
      intervalIdRef.current = setInterval(() => {
        console.log('tick');
        dispatch({ type: 'tick', timestamp: Date.now() });
      }, 100);
      dispatch({ type: 'start', timestamp: Date.now() });
    }
  }
  function partialClickHandler() {
    dispatch({ type: 'partial', timestamp: Date.now() });
  }
  function clearClickHandler() {
    dispatch({ type: 'clear' });
  }
  const startStopText = state.isRunning ? 'stop' : 'start';
  const partialEnabled = state.isRunning;
  const clearEnabled = !state.isRunning && state.partials.length != 0;
  const elapsed = state.tickTimestamp - state.startTimestamp;
  return (
    <div>
      <h2>{title}</h2>
      <p>{elapsed}</p>
      <button onClick={startStopClickHandler}>{startStopText}</button>
      <button onClick={partialClickHandler} disabled={!partialEnabled}>
        Partial
      </button>
      <button onClick={clearClickHandler} disabled={!clearEnabled}>
        Clear
      </button>
      <h3>Partials</h3>
      <ul>
        {state.partials.map((it, ix) => (
          <li key={ix}>{it}</li>
        ))}
      </ul>
    </div>
  );
}
