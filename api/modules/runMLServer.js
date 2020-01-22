require('dotenv').config();
const fetch = require('node-fetch');

const mlUrl = 'http://localhost:4000/test';

let time = null;

const invokeMl = () => {
    if (!time) {
        time = 6000;
        setTimeout(() => {
            time = null;
            fetch(mlUrl);
        }, time);
    }
};

module.exports = invokeMl;
