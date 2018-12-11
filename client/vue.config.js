module.exports = {
    devServer: {
        port: 8081,
        proxy: {
            //TODO
            "/good-beers": {
            target: "http://localhost:8080",
            secure: false
            }
        }
    }
};