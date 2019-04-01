const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
  entry: './src/electron-render/entry.tsx',
  target: 'electron-renderer',
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
      test: /\.scss|\.css$/,
      use: [{
        loader: "style-loader"
      }, {
        loader: "css-loader"
      }, {
        loader: "sass-loader",
        options: {
          sourceMap: true,
          includePaths: [path.resolve(__dirname, 'src/ui/')],
        }
      }],
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
      "@ui": path.resolve(__dirname, 'src/electron-render/ui'),
      "@pages": path.resolve(__dirname, 'src/electron-render/pages'),
      "@utils": path.resolve(__dirname, 'src/electron-render/utils'),
      "@entities": path.resolve(__dirname, 'src/electron-render/entities'),
      "@components": path.resolve(__dirname, 'src/electron-render/components'),
    },
    extensions: ['.tsx', '.ts', '.js'],
  },
  plugins: [
    new CleanWebpackPlugin(),
    new HtmlWebpackPlugin({
      filename: 'index.html',
      template: path.resolve(__dirname, './src/electron-render/index.html'),
      // favicon: path.resolve(__dirname, './src/electron-main/logo.ico'),
    }),
  ],
  optimization: {
    splitChunks: {
      chunks: 'all'
    }
  }
};
