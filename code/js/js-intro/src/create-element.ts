export function createElement(
  name: string,
  attrs?: null | Record<string, string | boolean>,
  ...children: Array<string | HTMLElement | Array<HTMLElement>>
): HTMLElement {
  const element = document.createElement(name);
  if (attrs) {
    for (const key in attrs) {
      const value = attrs[key];
      if (typeof value === 'string') {
        element.setAttribute(key, value);
      } else {
        if (value) {
          element.setAttribute(key, '');
        }
      }
    }
  }
  children.forEach(child => {
    if (typeof child === 'string') {
      element.appendChild(document.createTextNode(child));
    } else if (Array.isArray(child)) {
      child.forEach(it => {
        element.appendChild(it);
      });
    } else {
      element.appendChild(child);
    }
  });
  return element;
}

// Valid usages
createElement('div');
createElement('p', null, 'Hello World');
createElement('a', { href: 'https://www.typescriptlang.org' }, 'typescript');
createElement('button', { disabled: true }, 'Press me');
createElement('ul', null, createElement('li', null, 'Item 1'), createElement('li', null, 'Item 2'));
const items = ['item 1', 'item 2'];
createElement(
  'ul',
  null,
  items.map(item => createElement('li', null, item))
);
