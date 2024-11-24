import * as React from 'react';
import { createRoot } from 'react-dom/client';
//import {App} from './example-first/App'
// import { App } from './example-chat/App'
// import { App } from './example-counter-memo/App'
// import { App } from './example-lecture-18/App';
// import { App } from './example-lecture-18-counter/App';
// import { App } from './example-lecture-18-stopwatch/App';
// import { Message } from './example-lecture-18/models';
// import { App } from './example-lecture-18-leic51n/App';
// import {App} from './example-lecture-19/App'
// import {App} from './example-router-intro/App'
// import {App} from './example-stopwatch/App'
// import {App} from './example-fetch/App'
// import {App} from './example-lecture-20/App'
// import {App} from './example-useFetch/App'
//import {App} from './example-context/App'
// import {App} from './example-router-intro/App'
// import { App } from './example-lecture-21/App';
import { App } from './example-router-authentication/App';

const root = createRoot(document.getElementById('main-div'));
root.render(<App />);
