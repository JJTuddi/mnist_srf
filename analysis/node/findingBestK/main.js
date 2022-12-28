const ConfusionMatrixUtil = require('./util/ConfusionMatrixUtil');

const getAccuracy = (benchmark) => benchmark['otherDetails']['accuracy']

const ks = [10, 30, 50, 70, 100, 120, 150, 170, 200,
    220, 250, 270, 300, 320, 350, 370, 400];
const sortResult = ks.map(k => ConfusionMatrixUtil.extractConfusionMatrix(k))
    .sort((a, b) => -getAccuracy(a) + getAccuracy(b));

console.log(JSON.stringify(sortResult.map(r => ({
    k: r['k'],
    accuracy: r['otherDetails']['accuracy'],
}))));
