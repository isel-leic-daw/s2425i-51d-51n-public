// app.js
function app(_import) {
  const { add } = _import('./add.js');
  const { log } = _import('./logger.js');
  log(add(1, 2));
}

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

const _moduleTable = {
  './logger.js': logger,
  './add.js': add,
  './app.js': app,
};

function importModule(moduleName) {
  console.log(`Importing ${moduleName}`);
  const moduleValue = _moduleTable[moduleName];
  if (!moduleValue) {
    throw Error('Module does not exist');
  }
  if (typeof moduleValue == 'function') {
    console.log(`Running ${moduleName} to obtain the exports`);
    const moduleExportObject = {};
    moduleValue(importModule, moduleExportObject);
    _moduleTable[moduleName] = moduleExportObject;
    return moduleExportObject;
  }
  if (typeof moduleValue == 'object') {
    return moduleValue;
  }
  throw new Error('Invalid state');
}

importModule('./app.js');
