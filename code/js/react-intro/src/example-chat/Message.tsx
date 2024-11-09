import * as React from 'react';
type MessageProps = {
  sender: string;
  text: string;
  createdAt: Date;
};

const divStyle = {
  borderWidth: '2px',
  borderStyle: 'solid',
  margin: '30px',
  padding: '10px',
};
export function Message({ sender, text, createdAt }: MessageProps) {
  return (
    <div style={divStyle}>
      <p>
        {sender} says at {createdAt.toISOString()}:
      </p>
      <p style={{ fontWeight: 'bold' }}>{text}</p>
    </div>
  );
}
