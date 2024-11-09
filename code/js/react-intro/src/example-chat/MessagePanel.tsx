import * as React from 'react';
import { Message as MessageType } from './models';
import { Message } from './Message';

type MessagePanelProps = {
  title: string,
  messages: Array<MessageType>;
};
export function MessagePanel({ messages, title }: MessagePanelProps) {
  return (
    <>
      <h2>{title}</h2>
      {messages.map(it => (
        <Message text={it.text} sender={it.sender} createdAt={it.createdAt} key={it.id}/>
      ))}
    </>
  );
}
