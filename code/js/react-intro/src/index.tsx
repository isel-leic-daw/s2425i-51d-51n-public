import * as React from 'react';
import { createRoot } from 'react-dom/client';
//import {App} from './example-first/App'
// import { App } from './example-chat/App'
// import { App } from './example-counter-memo/App'
// import { App } from './example-lecture-18/App';
// import { App } from './example-lecture-18-counter/App';
// import { App } from './example-lecture-18-stopwatch/App';
// import { Message } from './example-lecture-18/models';
import { App } from './example-lecture-18-leic51n/App';

const root = createRoot(document.getElementById('main-div'));
let title = 0;
setInterval(() => {
  title += 1;
  root.render(<App title={title} />);
}, 1000);
