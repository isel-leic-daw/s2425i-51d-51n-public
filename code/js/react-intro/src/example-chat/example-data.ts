import { ChannelList } from './models';

const now = new Date();
export const exampleData: ChannelList = {
  items: [
    {
      id: '1',
      name: 'general',
      unread: 2,
      messages: [
        {
          id: '1',
          sender: 'Alice',
          text: "Hello. I'm Alice.",
          createdAt: now,
        },
        {
          id: '2',
          sender: 'Bob',
          text: "Hi. I'm Bob. Nice to meet you.",
          createdAt: now,
        },
      ],
    },
    {
      id: '2',
      name: 'DAW',
      unread: 0,
      messages: [
        {
          id: 'a',
          sender: 'Alice',
          text: 'Does anyone know what needs to be done for phase 2?',
          createdAt: now,
        },
        {
          id: 'b',
          sender: 'Carol',
          text: 'I believe we still need to learn a few more things.',
          createdAt: now,
        },
      ],
    },
  ],
};
