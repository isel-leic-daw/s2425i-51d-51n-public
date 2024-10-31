import URITemplate from 'urijs/src/URITemplate';

export function demo() {
  const template = new URITemplate('https://example.org/another/path/{id}');
  const uri = template.expand({
    id: 'abc-123/xyz/xpto',
  });

  console.log(uri);
}
