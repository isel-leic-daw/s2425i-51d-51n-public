import * as React from 'react';
import { Channel } from './models';
import { ChannelItem } from './ChannelItem';

type ChannelPanelProps = {
  channels: Array<Channel>;
  onClick?: (id: string) => void;
  selectedId: string,
};

export function ChannelPanel({ channels, onClick, selectedId }: ChannelPanelProps) {
    return (
        <ul>
            {channels.map(it => 
                <ChannelItem name={it.name} unread={it.unread} key={it.id} onClick={() => onClick(it.id)} selected={it.id === selectedId}/>
            )}
        </ul>
    )
}
