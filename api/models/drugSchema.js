const mongoose = require('mongoose');
const drugs = require('../constants/drugs');

const drugSchema = new mongoose.Schema({
    drug: {
        type: String,
        enum: drugs,
        required: true,
    },
    effect: {
        type: Number,
        required: true,
    },
});
module.exports = mongoose.model('drugSchema', drugSchema);
