const webpack = require('webpack');
const merge = require('webpack-merge');
const common = require('./webpack.common');
const path = require('path');
const os = require('os');

const networkInfo = os.networkInterfaces()['en0'];
let address;
if (networkInfo) {
  address = networkInfo[1].address
}

module.exports = merge(common, {
  devtool: 'inline-source-map',
  mode: 'development',
  devServer: {
    contentBase: './dist',
    hot: true,
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': JSON.stringify('development'),
      'process.env.IP_4': JSON.stringify(address),
    })
  ],
  devServer: {
    https: false,
    host: '0.0.0.0',
    contentBase: path.resolve(__dirname, './src/assets/'),
    allowedHosts: [
      '.local',
    ],
  },
});
