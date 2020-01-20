const mongoose = require('mongoose');

const authSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true,
    },
    accessToken: {
        type: String,
    },
    refreshToken: {
        type: String,
    },
});

module.exports = authSchema;
