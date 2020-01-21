const diseases = require('../api/constants/diseases');
const drugs = require('../api/constants/drugs');

const connStr = "mongostring";
const mongoose = require('../api/node_modules/mongoose');

mongoose.connect(connStr, () => {
    console.log('Connected to Mongo');
});

const dataSchema = require('../api/models/dataSchema');

const dosages = [100, 150, 200, 300, 350, 500, 550, 600];

const getRandInc = (min, max) => {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min; 
}

const generateData = async (n) => {
        let data = [];
        for (let i = 0; i < n; i++) {
            let dataObject = new dataSchema({
                disease: diseases[getRandInc(0, diseases.length - 1)],
                medicine: drugs[getRandInc(0, drugs.length - 1)],
                dosage: dosages[getRandInc(0, dosages.length - 1)],
                age: getRandInc(13, 80),
                weight: getRandInc(40, 100),
                comment: `Datapoint #${i+1}`,
                result: !(getRandInc(1, 10) === 5),
            });
            await dataObject.save(); 
            // data.push(dataObject);
            // process.stdout.write(dataObject.toString());
        }
        //return data;
}

generateData(1000);