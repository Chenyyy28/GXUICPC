const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  publicPath: '/',
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    port: 80,
    proxy: {
      '/url': {
        target: 'http://localhost:8090',
        changeOrigin: true,
        ws: true,
        webSocketServer: false,
        pathRewrite: {
          '^/url': ''
        }
      }
    }
  }
})
