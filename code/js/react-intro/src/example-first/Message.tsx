import * as React from 'react';

type MessageProps = {
  sender: string;
  text: string;
  createdAt: Date;
};
/*
 A *component* is a function:
 - From a *model* (the "props"), which is typically just an object.
 - Into a *view* defined as a tree of React elements 
   (i.e. a tree of virtual DOM elements)
 */
function Message({ text, sender, createdAt }: MessageProps): React.ReactElement {
  return React.createElement(
    'div',
    { className: 'some-class' },
    React.createElement('p', {}, `${sender} says at ${createdAt.toISOString()}`),
    React.createElement('p', {}, text)
  );
}

function Message2({ text, sender, createdAt }: MessageProps): React.ReactElement {
  const senderLine = `${sender} says at ${createdAt.toISOString()}`;
  return (
    <div className="some-class">
      <p>{senderLine}</p>
      <p>{text}</p>
    </div>
  );
}

// from the HTTP API model
export type ApiMessage = {
  id: string;
  sender: string;
  text: string;
  createdAt: Date;
};
type MessageListProps = {
  messages: Array<ApiMessage>;
};

export function MessageList({ messages }: MessageListProps): React.ReactElement {
  // Preparations to create the element tree
  const messageElements = messages.map(it => (
    <Message key={it.id} sender={it.sender} text={it.text} createdAt={it.createdAt} />
  ));
  // Create and return the element tree
  return <div>{messageElements}</div>;
}
