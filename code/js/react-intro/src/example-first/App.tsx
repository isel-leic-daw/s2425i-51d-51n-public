import * as React from 'react';
import { Counter } from './Counter';
import { MessageList, ApiMessage } from './Message';

const now = new Date();
const demoMessages: Array<ApiMessage> = [
  {
    id: 'aaa',
    sender: 'Alice',
    text: 'hello',
    createdAt: now,
  },
  {
    id: 'bbb',
    sender: 'Bob',
    text: 'Hi. Nice to meet you.',
    createdAt: now,
  },
];
export function App(): React.ReactElement {
  return <MessageList messages={demoMessages} />;
}
