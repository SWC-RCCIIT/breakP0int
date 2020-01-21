require('dotenv').config();
const jwt = require('jsonwebtoken');

module.exports = async token => {
    let ret;
    await jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, err => {
        if (err) ret = false;
        ret = true;
    });
    return ret;
};
