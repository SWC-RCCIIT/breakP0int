const mongoose = require('mongoose');
const diseases = require('../constants/diseases');

const dataSchema = new mongoose.Schema({
    disease: {
        type: String,
        enum: diseases,
        required: true,
    },
    medicine: {
        type: String,
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
});
module.exports = mongoose.model('dataSchema', dataSchema);
