import * as React from 'react';
import { useEffect, useReducer } from 'react';

// The State
type State =
  | { type: 'begin' }
  | { type: 'loading'; url: string }
  | { type: 'loaded'; payload: string; url: string }
  | { type: 'error'; error: Error; url: string };

// The Action
type Action =
  | { type: 'start-loading'; url: string }
  | { type: 'loading-success'; payload: string; url: string }
  | { type: 'loading-error'; error: Error };

function unexpectedAction(action: Action, state: State) {
  console.log(`Unexpected action ${action.type} in state ${state.type}`);
  return state;
}
// The reducer
function reducer(state: State, action: Action): State {
  switch (action.type) {
    case 'start-loading':
      return { type: 'loading', url: action.url };
    case 'loading-success':
      if (state.type !== 'loading') {
        return unexpectedAction(action, state);
      }
      return { type: 'loaded', payload: action.payload, url: action.url };
    case 'loading-error':
      if (state.type !== 'loading') {
        return unexpectedAction(action, state);
      }
      return { type: 'error', error: action.error, url: state.url };
  }
}

// The props
type FetchProps = {
  url: string;
};
// The component
export function Fetch({ url }: FetchProps) {
  const [state, dispatch] = useReducer(reducer, { type: 'begin' });
  useEffect(() => {
    if (!url) {
      return;
    }
    let cancelled = false;
    const abortController = new AbortController();
    async function doFetch() {
      dispatch({ type: 'start-loading', url: url });
      try {
        const resp = await fetch(url, { signal: abortController.signal });
        if (cancelled) {
          return;
        }
        const json = await resp.json();
        if (!cancelled) {
          dispatch({ type: 'loading-success', url: url, payload: JSON.stringify(json, null, 2) });
        }
      } catch (error) {
        console.log(`catch error: ${error}`)
        if (!cancelled) {
          dispatch({ type: 'loading-error', error: error });
        }
      }
    }
    doFetch();
    return () => {
      abortController.abort();
      cancelled = true;
    };
  }, [url]);
  switch (state.type) {
    case 'begin':
      return <p>Idle</p>;
    case 'loading':
      return <p>loading ...</p>;
    case 'loaded':
      return <textarea readOnly value={state.payload} rows={40} cols={80} />;
    case 'error':
      return <p>Error: {state.error.message}</p>;
  }
}

// |---------------- 10 ------------------------------>(dispatch(loading-success))
//     |---2---->(dispatch(loading-success))
//
// start-loading delay/5
// ...
// start-loading delay/10
// ...
// loading-success (delay/5) -> state=loaded
// ...
// loading-success (delay/10) -> unexpected action
