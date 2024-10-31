export default {
  mode: 'production',
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
