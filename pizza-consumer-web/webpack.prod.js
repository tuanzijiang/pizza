const merge = require('webpack-merge');
const common = require('./webpack.common');
const webpack = require('webpack');
const WorkboxPlugin = require('workbox-webpack-plugin');
const os = require('os');

const networkInfo = os.networkInterfaces()['en0'];
let address = null;
if (networkInfo) {
  networkInfo.forEach(v => {
    if (v.family === 'IPv4') {
      address = v.address
    }
  });
}


module.exports = merge(common, {
  mode: 'production',
  devtool: 'source-map',
  plugins: [
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': JSON.stringify('production'),
      'process.env.IP_4': JSON.stringify(address),
    }),
    new WorkboxPlugin.GenerateSW({
      clientsClaim: true,
      skipWaiting: true
    }),
  ],
});
