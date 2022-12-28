const fs = require("fs");

const numberOfClasses = 10;

const computeAccuracy = confusionMatrix => {
    let total = 0;
    let sameClass = 0;
    for(let i = 0; i < numberOfClasses; i++) {
        for (let j = 0; j < numberOfClasses; j++) {
            total += confusionMatrix[i][j];
            if (i === j) {
                sameClass += confusionMatrix[i][j];
            }
        }
    }
    return sameClass / total;
}

const createConfusionMatrix = (k, confusionMatrix) => ({k, confusionMatrix,
    otherDetails: {
        accuracy: computeAccuracy(confusionMatrix)
    }
});

const getBestKObject = (k, dataset) => JSON.parse(fs.readFileSync("./{{DATASET}}/bestk_{{K}}.fit.json".replace("{{K}}", k).replace("{{DATASET}}", dataset)).toString());

const extractConfusionMatrix = (k) => {
    const from30 = getBestKObject(k, 30);
    const from120 = getBestKObject(k, 120);
    const matrix = [...new Array(numberOfClasses)].map(() => [... new Array(numberOfClasses)].map(() => 0));
    for (let i = 0; i < numberOfClasses; i++) {
        for (let j = 0; j < numberOfClasses; j++) {
            matrix[i][j] = from30['benchmarkResults']['confusionMatrix'][i][j] + from120['benchmarkResults']['confusionMatrix'][i][j]
        }
    }
    return createConfusionMatrix(k, matrix);
}

module.exports = {
    computeAccuracy,
    extractConfusionMatrix
}
