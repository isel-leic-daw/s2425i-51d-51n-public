import * as React from 'react';
type ChannelItemProps = {
  name: string;
  unread: number;
  selected: boolean;
  onClick?: () => void;
};

const normalStyle = {
  // nothing for now
};

const selectedStyle = {
  fontWeight: 'bold',
};

export function ChannelItem({ name, unread, onClick, selected }: ChannelItemProps) {
  const styleToUse = selected ? selectedStyle : normalStyle
  const suffix = unread === 0 ? '' : `(${unread})`;
  const line = `${name}${suffix}`;
  return (
    <li onClick={onClick}>
      <span style={styleToUse}>{line}</span>
    </li>
  );
}
