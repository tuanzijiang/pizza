const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
  entry: './src/entry.tsx',
  output: {
    filename: '[name].[hash].bundle.js',
    chunkFilename: '[name].[hash].chunk.js',
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/'
  },
  module: {
    rules: [{
      test: /\.less$/,
      use: [
        'style-loader',
        'css-loader',
        'less-loader'
      ],
      exclude: /node_modules/
    }, {
      test: /\.(png|gif|jpg|svg|jpeg|woff|woff2|eot|ttf|otf)$/,
      use: [
        'file-loader'
      ],
      exclude: /node_modules/
    }, {
      test: /\.tsx?$/,
      use: 'awesome-typescript-loader',
      exclude: /node_modules/
    }, {
      enforce: "pre",
      test: /\.js$/,
      loader: "source-map-loader"
    }]
  },
  resolve: {
    alias: {
      "@biz-components": path.resolve(__dirname, 'src/biz-components/'),
      "@components": path.resolve(__dirname, 'src/components/'),
      "@src": path.resolve(__dirname, 'src/'),
      "@ui": path.resolve(__dirname, 'src/ui'),
      "@net": path.resolve(__dirname, 'src/net/'),
      "@utils": path.resolve(__dirname, 'src/utils/'),
      "@assets": path.resolve(__dirname, 'src/assets/'),
      "@schemas": path.resolve(__dirname, 'src/schemas/'),
      "@store": path.resolve(__dirname, 'src/store/'),
      "@entity": path.resolve(__dirname, 'src/entity/'),
    },
    extensions: ['.tsx', '.ts', '.js'],
  },
  plugins: [
    new CleanWebpackPlugin(['dist']),
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, './src/templates/index.template.html'),
    }),
  ],
  optimization: {
    splitChunks: {
      chunks: 'all'
    }
  }
};
