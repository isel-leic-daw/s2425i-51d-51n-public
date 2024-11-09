export type Message = {
    id: string,
    sender: string,
    text: string,
    createdAt: Date,
}

export type Channel = {
  id: string;
  name: string;
  unread: number;
  messages: Array<Message>
};

export type ChannelList = {
    items: Array<Channel>
}
