const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const WebpackModules = require('webpack-modules');

module.exports = {
    entry: path.resolve(__dirname, 'jsx', 'App.jsx'),
    output: {
        path: path.resolve(__dirname, 'src/main/resources/static/js/'),
        filename: 'build.js'
    },
    plugins: [new MiniCssExtractPlugin(), new WebpackModules()],
    resolve: {
        fallback: {
            'stream': require.resolve('stream-browserify'),
            'buffer': require.resolve('buffer/'),
            "tty": require.resolve("tty-browserify"),
            "os": require.resolve("os-browserify/browser")
        },
        modules: [path.resolve(__dirname, 'node_modules')],
        extensions: ['.js', '.jsx', '.json', '.ts']
    },
    module: {
        rules: [
            {
                test: /\.mjs$/,
                include: path.resolve(__dirname, 'node_modules'),
                type: 'javascript/auto'
            },
            {
                test: /\.(js|jsx)$/,
                exclude: path.resolve(__dirname, 'node_modules'),
                loader: 'babel-loader'
            },
            {
                test: /\.(s*|css)$/,
                use: [
                    'style-loader',
                    'css-loader',
                    'sass-loader']
            },
            {
                test: /(\.less)$/,
                use: [
                    {
                        loader: "iso-morphic-style-loader",
                        options: {
                            singleton: true
                        }
                    },
                    {
                        loader: "css-loader",
                        options: {
                            modules : {
                                localIdentName: '[local]_[hash:base64:5]'
                            },
                            importLoader: 1,
                            sourceMap: false
                        }
                    },
                    'less-loader'
                ]
            },
            {test: /\.(gif|pdf|png|jpe?g)(\?.*)?$/, use: 'url?limit=8182'},
            {test: /\.(png|woff|woff2|eot|ttf|svg)(\?.*)?$/, use: 'url-loader?limit=100000'}
        ]
    }
};