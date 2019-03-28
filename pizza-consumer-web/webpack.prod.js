const merge = require('webpack-merge');
const common = require('./webpack.common');
const webpack = require('webpack');
const WorkboxPlugin = require('workbox-webpack-plugin');


module.exports = merge(common, {
  mode: 'production',
  devtool: 'source-map',
  plugins: [
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': JSON.stringify('production'),
    }),
    new WorkboxPlugin.GenerateSW({
      clientsClaim: true,
      skipWaiting: true
    }),
  ],
});
