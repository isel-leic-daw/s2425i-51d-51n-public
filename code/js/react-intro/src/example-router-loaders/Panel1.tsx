import * as React from 'react';
import { useLoaderData } from 'react-router-dom';

export function Panel1() {
  const data = useLoaderData() as any;
  return (
    <div>
      <h2>Panel 1</h2>
      <p>{data.message}</p>
    </div>
  );
}
