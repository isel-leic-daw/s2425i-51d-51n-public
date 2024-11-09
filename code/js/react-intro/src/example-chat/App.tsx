import * as React from 'react';
import { useState, useCallback } from 'react';
import { exampleData } from './example-data';
import { ChannelPanel } from './ChannelPanel';
import { MessagePanel } from './MessagePanel';
import { Channel } from './models';

export function App() {
  const [channel, setChannel] = useState<Channel>(exampleData.items[0]);
  const onClick = useCallback(
    id => {
      const channel = exampleData.items.find(it => it.id === id);
      if (channel) {
        setChannel(channel);
      }
    },
    [setChannel]
  );
  return (
    <div style={{ display: 'flex' }}>
      <div style={{flex: "1", padding: "10px"}}>
        <ChannelPanel channels={exampleData.items} onClick={onClick} selectedId={channel.id}/>
      </div>
      <div>
        <MessagePanel messages={channel.messages} title={channel.name} />
      </div>
    </div>
  );
}
