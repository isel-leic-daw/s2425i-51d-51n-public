// add.js
function add(_import, _exports) {
  const { log } = _import('./logger.js');

  const name = 'add';
  log(name);

  _exports.add = function add(x, y) {
    log(`Adding ${x} with ${y}`);
    return x + y;
  };
}

// logger.js
function logger(_import, _exports) {
  const name = 'logger';
  console.log(name);

  _exports.log = function log(msg) {
    console.log(msg);
  };
}

// app.js
function app(_import) {
  const { add } = _import('./add.js');
  const { log } = _import('./logger.js');
  log(add(1, 2));
}

const _moduleMap = {
  './add.js': add,
  './logger.js': logger,
  './app.js': app,
};

function importModule(moduleName) {
  console.log(`importModule(${moduleName})`);
  const value = _moduleMap[moduleName];
  if (!value) {
    throw Error(`Module ${moduleName} does not exist`);
  }
  if (typeof value === 'function') {
    console.log(`First import for ${moduleName}, calling its function`);
    const exportObject = {};
    value(importModule, exportObject);
    _moduleMap[moduleName] = exportObject;
    return exportObject;
  }
  if (typeof value === 'object') {
    console.log(`Subsequent import for ${moduleName}, using the exports object`);
    return value;
  }
  throw Error('Invalid state. This should never happen.');
}

importModule('./app.js');
