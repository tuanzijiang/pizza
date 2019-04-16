const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const WebpackPwaManifest = require('webpack-pwa-manifest');

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
      "@services": path.resolve(__dirname, 'src/services/'),
    },
    extensions: ['.tsx', '.ts', '.js'],
  },
  plugins: [
    new CleanWebpackPlugin(['dist']),
    new HtmlWebpackPlugin({
      filename: 'index.html',
      template: path.resolve(__dirname, './src/index.html'),
      favicon: path.resolve(__dirname, './src/assets/logo.ico'),
    }),
    new WebpackPwaManifest({
      name: 'Pizza',
      short_name: 'Pizza',
      description: 'My awesome pizza Web App!',
      background_color: '#ffffff',
      crossorigin: 'use-credentials', //can be null, use-credentials or anonymous
      inject: true,
      fingerprints: true,
      icons: [
        {
          src: path.resolve(__dirname, './src/assets/logo.ico'),
          sizes: [128] // multiple sizes
        },
      ]
    })
  ],
  optimization: {
    splitChunks: {
      chunks: 'all'
    }
  }
};
