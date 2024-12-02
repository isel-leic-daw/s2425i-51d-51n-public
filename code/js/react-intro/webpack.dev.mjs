import { server } from 'typescript';

export default {
  mode: 'development',
  devServer: {
    historyApiFallback: true,
    port: 3000,
    compress: false,
    proxy: [
      {
        context: ['/api'],
        target: 'http://localhost:8180',
        onProxyRes: (proxyRes, req, res) => {
          console.log('onProxyRes');
          proxyRes.on('close', () => {
            console.log('on proxyRes close');
            if (!res.writableEnded) {
              res.end();
            }
          });
          res.on('close', () => {
            console.log('on res close');
            proxyRes.destroy();
          });
        },
      },
    ],
  },
  resolve: {
    extensions: ['.js', '.ts', '.tsx'],
  },
  plugins: [],
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
    ],
  },
};
