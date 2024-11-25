import { server } from "typescript";

export default {
    mode: 'development',
    entry: {
      main: './src/index.ci.tsx'
    },
    devServer: {
      historyApiFallback: true,
      port: 3000,
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