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

console.log("\n")

console.log(
  "Knn and DistanceTransform's accuracy " + (ConfusionMatrixUtil.computeAccuracy([
    [967, 0, 1, 2, 0, 0, 2, 0, 8, 0],
    [1, 451, 1, 24, 0, 0, 5, 0, 653, 0],
    [148, 0, 709, 49, 4, 0, 35, 0, 87, 0],
    [109, 0, 17, 786, 0, 0, 1, 2, 92, 3],
    [87, 0, 3, 8, 457, 0, 42, 0, 207, 178],
    [379, 0, 3, 138, 1, 36, 7, 0, 323, 5],
    [202, 0, 2, 2, 2, 0, 685, 0, 65, 0],
    [107, 0, 17, 15, 4, 0, 2, 572, 211, 100],
    [78, 0, 3, 66, 1, 0, 7, 2, 814, 3],
    [90, 0, 2, 18, 4, 0, 5, 2, 269, 619],
  ]) * 100) + "%"
);

console.log(
  "Cnn's accuracy " + (ConfusionMatrixUtil.computeAccuracy([
    [964, 1, 1, 3, 0, 2, 4, 1, 3, 1],
    [0, 1106, 2, 1, 2, 1, 2, 2, 19, 0],
    [4, 5, 983, 7, 4, 2, 7, 4, 16, 0],
    [2, 0, 10, 940, 1, 19, 0, 6, 23, 9],
    [1, 2, 5, 3, 920, 2, 9, 11, 3, 26],
    [2, 2, 1, 20, 1, 838, 10, 5, 12, 1],
    [8, 3, 2, 0, 5, 15, 922, 0, 3, 0],
    [0, 3, 14, 20, 3, 2, 0, 969, 5, 12],
    [6, 0, 5, 9, 5, 7, 5, 4, 928, 5],
    [9, 3, 0, 6, 15, 5, 0, 10, 12, 949],
  ]) * 100) + "%"
);