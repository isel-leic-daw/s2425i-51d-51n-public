import * as React from 'react';
import { Message } from './models';
import { MessageComponent } from './MessageComponent';

type MessageListComponentProps = {
  title: string;
  messages: Array<Message>;
};
export function MessageListComponent({ title, messages }: MessageListComponentProps): React.ReactElement {
  const messagesToShow = messages.length <= 3 ? messages : messages.slice(-4, -1);
  const messageElements = messagesToShow.map(it => (
    <MessageComponent sender={it.sender} text={it.text} createdAt={it.createdAt} key={it.id} />
  ));
  return (
    <div>
      <h2>{title}</h2>
      {messageElements}
    </div>
  );
}

/*
 <div className="someClass"> -> React.createElement('div', {className: "someClass"})
 <MessageComponent sender="alice" /> -> React.createElement(MessageComponent, {sender:"alice"})
 */
