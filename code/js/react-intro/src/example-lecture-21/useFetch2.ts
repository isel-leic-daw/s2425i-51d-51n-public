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
      return { type: 'loaded', payload: action.payload, url: state.url };
    case 'loading-error':
      if (state.type !== 'loading') {
        return unexpectedAction(action, state);
      }
      return { type: 'error', error: action.error, url: state.url };
  }
}

export function useFetch(url: string): State {
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
        const json = await resp.json();
        if (!cancelled) {
          dispatch({ type: 'loading-success', url: url, payload: JSON.stringify(json, null, 2) });
        }
      } catch (error) {
        console.log(`catch error: ${error}`);
        if (!cancelled) {
          dispatch({ type: 'loading-error', error: error });
        }
      }
    }
    doFetch();
    return () => {
      cancelled = true;
      abortController.abort();
    };
  }, [url]);
  return state;
}
