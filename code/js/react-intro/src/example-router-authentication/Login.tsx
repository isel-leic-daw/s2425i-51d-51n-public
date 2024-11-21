import React, { useReducer } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAuthentication } from './authentication';
import { authenticate } from './authenticate';

type State =
  | { tag: 'editing'; error?: string; inputs: { username: string; password: string } }
  | { tag: 'submitting'; username: string }
  | { tag: 'redirect' };

type Action =
  | { type: 'edit'; inputName: string; inputValue: string }
  | { type: 'submit' }
  | { type: 'error'; message: string }
  | { type: 'success' };

function logUnexpectedAction(state: State, action: Action) {
  console.log(`Unexpected action '${action.type} on state '${state.tag}'`);
}

function reduce(state: State, action: Action): State {
  switch (state.tag) {
    case 'editing':
      if (action.type === 'edit') {
        return { tag: 'editing', error: undefined, inputs: { ...state.inputs, [action.inputName]: action.inputValue } };
      } else if (action.type === 'submit') {
        return { tag: 'submitting', username: state.inputs.username };
      } else {
        logUnexpectedAction(state, action);
        return state;
      }

    case 'submitting':
      if (action.type === 'success') {
        return { tag: 'redirect' };
      } else if (action.type === 'error') {
        return { tag: 'editing', error: action.message, inputs: { username: state.username, password: '' } };
      } else {
        logUnexpectedAction(state, action);
        return state;
      }

    case 'redirect':
      logUnexpectedAction(state, action);
      return state;
  }
}

export function Login() {
  const [state, dispatch] = useReducer(reduce, { tag: 'editing', inputs: { username: '', password: '' } });
  const [, setUsername] = useAuthentication();
  const location = useLocation();
  if (state.tag === 'redirect') {
    return <Navigate to={location.state?.source || '/'} replace={true} />;
  }
  function handleChange(ev: React.FormEvent<HTMLInputElement>) {
    dispatch({ type: 'edit', inputName: ev.currentTarget.name, inputValue: ev.currentTarget.value });
  }
  async function handleSubmit(ev: React.FormEvent<HTMLFormElement>) {
    ev.preventDefault();
    if (state.tag !== 'editing') {
      return;
    }
    dispatch({ type: 'submit' });
    const username = state.inputs.username;
    const password = state.inputs.password;
    try {
      const result = await authenticate(username, password);
      if (!result) {
        dispatch({ type: 'error', message: 'invalid username or password' });
        return
      }
      console.log(result)
      setUsername(result);
      dispatch({ type: 'success' });
    } catch (error) {
        dispatch({type: 'error', message: 'operation could not be completed'})
    }
  }

  const username = state.tag === 'submitting' ? state.username : state.inputs.username;
  const password = state.tag === 'submitting' ? '' : state.inputs.password;
  return (
    <form onSubmit={handleSubmit}>
      <fieldset disabled={state.tag !== 'editing'}>
        <div>
          <label htmlFor="username">Username</label>
          <input id="username" type="text" name="username" value={username} onChange={handleChange} />
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <input id="password" type="text" name="password" value={password} onChange={handleChange} />
        </div>
        <div>
          <button type="submit">Login</button>
        </div>
      </fieldset>
      {state.tag === 'editing' && state.error}
    </form>
  );
}
