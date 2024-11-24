import { server } from "typescript";

export default {
    mode: 'development',
    devServer: {
      historyApiFallback: true,
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