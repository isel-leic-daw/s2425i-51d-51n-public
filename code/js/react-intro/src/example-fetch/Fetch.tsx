import { abort } from 'process';
import * as React from 'react';
import { useEffect, useReducer } from 'react';

type FetchProps = {
  uri: string;
};

type State =
  | { type: 'start' }
  | { type: 'loading'; uri: string }
  | { type: 'success'; uri: string; json: string }
  | { type: 'error'; uri: string; error: Error };

type Action =
  | { type: 'started-loading'; uri: string }
  | { type: 'success'; json: string }
  | { type: 'error'; error: Error }
  | { type: 'cancel' };

function unexpectedAction(action: Action, state: State): State {
  console.log(`unexpected action ${action.type} on state ${state.type}`);
  return state;
}

function reducer(state: State, action: Action): State {
  console.log(`handling ${action.type} on ${state.type}`);
  switch (action.type) {
    case 'started-loading':
      return { type: 'loading', uri: action.uri };
    case 'success':
      switch (state.type) {
        case 'loading':
          return { ...state, type: 'success', json: action.json };
        default:
          return unexpectedAction(action, state);
      }
    case 'error':
      switch (state.type) {
        case 'loading':
          return { ...state, type: 'error', error: action.error };
        default:
          return unexpectedAction(action, state);
      }
    case 'cancel':
      return { type: 'start' };
  }
}

const firstState: State = { type: 'start' };

export function Fetch({ uri }: FetchProps) {
  const [state, dispatch] = useReducer(reducer, firstState);
  useEffect(() => {
    if (uri === '') {
      dispatch({ type: 'cancel' });
      return;
    }
    const abort = new AbortController();
    let cancelled = false;
    async function doFetch() {
      dispatch({ type: 'started-loading', uri: uri });
      try {
        const resp = await fetch(uri, { signal: abort.signal });
        const json = await resp.json();
        console.log(cancelled);
        if (!cancelled) {
          dispatch({ type: 'success', json: json });
        }
      } catch (error) {
        if (!cancelled) {
          dispatch({ type: 'error', error: error });
        }
      }
    }
    doFetch();
    return () => {
      console.log('cleanup');
      abort.abort();
      cancelled = true;
    };
  }, [uri, dispatch]);
  switch (state.type) {
    case 'start':
      return <p>Idle</p>;
    case 'loading':
      return <p>loading...</p>;
    case 'error':
      return <p>Error: {state.error.message}</p>;
    case 'success': {
      return (
        <div>
          <textarea value={JSON.stringify(state.json)} readOnly cols={80} rows={40}></textarea>
        </div>
      );
    }
  }
}
