// Entry point to be used by the CI process
import * as React from 'react';
import { createRoot } from 'react-dom/client';
import { App } from './example-router-authentication/App';

const root = createRoot(document.getElementById('main-div'));
root.render(<App />);
