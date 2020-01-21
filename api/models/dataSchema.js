const mongoose = require('mongoose');
const diseases = require('../constants/diseases');
const drugs = require('../constants/drugs');

const dataSchema = new mongoose.Schema({
    disease: {
        type: String,
        enum: diseases,
        required: true,
    },
    medicine: {
        type: String,
        enum: drugs,
        required: true,
    },
    dosage: {
        type: Number,
        required: true,
    },
    age: {
        type: Number,
        required: true,
    },
    weight: {
        type: Number,
        required: true,
    },
    comments: {
        type: String,
    },
    result: {
        type: Boolean,
        required: true,
        default: false,
    },
});
module.exports = mongoose.model('dataSchema', dataSchema);
