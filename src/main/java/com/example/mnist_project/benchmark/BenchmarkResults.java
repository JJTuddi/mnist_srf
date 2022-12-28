package com.example.mnist_project.benchmark;

import com.example.mnist_project.models.ModelType;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.mnist_project.util.Constants.numberOfClasses;
import static lombok.AccessLevel.NONE;

@Getter
@Builder
public class BenchmarkResults {

    private ModelType type;
    @Getter(NONE)
    private ConfusionMatrix confusionMatrix;

    public BenchmarkResults(ModelType type, ConfusionMatrix confusionMatrix) {
        this.type = type;
        this.confusionMatrix = confusionMatrix;
    }

    public int[][] getConfusionMatrix() {
        return Optional.ofNullable(confusionMatrix)
                .map(ConfusionMatrix::getMatrix)
                .map(int[][]::clone)
                .orElse(null);
    }

    public double getOverallAccuracy() {
        return Optional.ofNullable(confusionMatrix)
                .map(ConfusionMatrix::getMatrix)
                .map(confMatrix -> {
                    double score = 0;
                    double totalSum = 0;
                    for (int i = 0; i < confMatrix.length; i++) {
                        for (int j = 0; j < confMatrix[i].length; j++) {
                            totalSum += confMatrix[i][j];
                            if (i == 0) {
                                score += confMatrix[i][j];
                            }
                        }
                    }
                    return score / totalSum;
                })
                .orElse(0.0);
    }

    public Map<Integer, Metric> getMetricsOfDigits() {
        Map<Integer, Metric> result = new HashMap<>();
        for (int currentClass = 0; currentClass < numberOfClasses; currentClass++) {
            result.put(currentClass, confusionMatrix.getBasicMetricsOfClass(currentClass));
        }
        return result;
    }

}
