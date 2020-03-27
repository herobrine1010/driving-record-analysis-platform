module.exports = {
    baseUrl: './',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer:{
        proxy:{
            '/api':{
                target:"http://localhost:8028",
                changeOrigin:true,
                secure:false,
                pathRewrite:{
                    '^/api':""
                }
            }
        }
    },

    // devServer: {
    //     proxy: {
    //         '/api':{
    //             target:'http://jsonplaceholder.typicode.com',
    //             changeOrigin:true,
    //             pathRewrite:{
    //                 '/api':''
    //             }
    //         }
    //     }
    // }
}
