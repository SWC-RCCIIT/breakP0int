require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');

const dataSchema = require('./models/dataSchema');
const drugSchema = require('./models/drugSchema');
const verifyToken = require('./modules/verifyTokenMid');
const invokeMl = require('./modules/runMLServer');

const app = express();
app.use(express.json());

mongoose.connect(
    process.env.MONGO_MAIN,
    {
        useNewUrlParser: true,
        useUnifiedTopology: true,
    },
    () => {
        console.log('Connected to mongo');
    },
);

app.post('/prescribed/:disease', async (req, res) => {
    if (!(await verifyToken(req.body.accessToken))) {
        res.status(403).send({ text: 'InvalidAccess' });
        return;
    }
    const { disease } = req.params;
    const { medicine, dosage, age, weight, result } = req.body;
    let data = new dataSchema({
        disease,
        medicine,
        dosage,
        age,
        weight,
        result,
    });
    await data.save();
    invokeMl();
    res.send(data.toObject({ virtuals: true }));
});

app.get('/result/:drug', (req, res) => {
    const { drug } = req.query;
    drugSchema.findOne({ drug }, (err, doc) => {
        if (err) return res.sendStatus(404);
        res.send(doc);
    });
});

app.listen(process.env.MAIN_PORT, () => {
    console.log('Main server started');
});
