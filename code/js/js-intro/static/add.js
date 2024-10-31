import { log } from './logger.js';

const name = 'add';
log(name);

export function add(x, y) {
  log(`Adding ${x} with ${y}`);
  return x + y;
}
