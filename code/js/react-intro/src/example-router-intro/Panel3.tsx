import React from 'react';
import { useParams } from 'react-router-dom';

export function Panel3() {
  const params = useParams();
  return (
    <div>
      <h2>Panel 3</h2>
      <pre>{JSON.stringify(params)}</pre>
    </div>
  );
}
