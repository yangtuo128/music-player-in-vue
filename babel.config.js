module.exports = {
  presets: [
    ['@vue/app', {
      useBuiltIns: 'entry',
      corejs: 2
    }]
  ],
  plugins: [
    '@babel/plugin-transform-runtime',
    '@babel/plugin-transform-optional-chaining'
  ]
};
