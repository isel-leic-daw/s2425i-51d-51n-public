import * as React from 'react' 
import { useFetch } from './useFetch'

export function SomeComponentUsingFetch({url}:{url:string}) {
    const fetchState = useFetch(url) 
    switch (fetchState.type) {
        case 'begin':
          return <p>Idle</p>;
        case 'loading':
          return <p>loading ...</p>;
        case 'loaded':
          return <textarea readOnly value={fetchState.payload} rows={40} cols={80} />;
        case 'error':
          return <p>Error: {fetchState.error.message}</p>;
      }
}
