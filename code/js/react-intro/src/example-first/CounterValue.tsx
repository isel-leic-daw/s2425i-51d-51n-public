import * as React from 'react';

const nonNegativeStyle = {
  padding: '10px',
  display: 'inline-block',
  width: '10px',
  color: 'blue',
};
const negativeStyle = {
  padding: '10px',
  display: 'inline-block',
  width: '10px',
  color: 'red',
};

type CounterValueProps = {
  value: number;
};
export function CounterValue({ value }: CounterValueProps) {
  const styleToUse = value < 0 ? negativeStyle : nonNegativeStyle;
  return <span style={styleToUse}>{value}</span>;
}
