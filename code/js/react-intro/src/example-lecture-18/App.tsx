import * as React from 'react';
import { Message } from './models';
import { MessageListComponent } from './MessageListComponent';

const now = new Date();
const exampleMessages: Array<Message> = [
  {
    id: '1',
    sender: 'alice',
    text: "Hello. I'm Alice",
    createdAt: now,
  },
  {
    id: '2',
    sender: 'bob',
    text: "Hello. I'm Bob. Nice to meet you.",
    createdAt: now,
  },
];

export function App({messages}: {messages: Array<Message>}) {
  return <div>
    <MessageListComponent title="Example" messages={messages} />
  </div>;
}
