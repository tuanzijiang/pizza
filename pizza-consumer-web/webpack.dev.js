const webpack = require('webpack');
const merge = require('webpack-merge');
const common = require('./webpack.common');
const path = require('path');
const os = require('os');
const argv = require('yargs').argv;

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
      'process.env.isPc': JSON.stringify(argv.isPc === 'true'),
    })
  ],
  devServer: {
    https: false,
    host: '0.0.0.0',
    openPage: '/index.template.html',
    contentBase: path.resolve(__dirname, './src/assets/'),
    allowedHosts: [
      '.local',
    ],
  },
});
