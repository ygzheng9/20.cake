const path = require('path');

module.exports = {
  entry: {
    bundle: './src/index.js'
  },

  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, '../webapp/assets/js')
  },

  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: [/node_modules/],
        use: [{
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env'],
            plugins: ['@babel/plugin-proposal-object-rest-spread',"@babel/plugin-proposal-class-properties"]
          } }]
      }
    ]
  }
};
