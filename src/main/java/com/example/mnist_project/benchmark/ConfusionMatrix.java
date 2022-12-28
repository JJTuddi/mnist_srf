package com.example.mnist_project.benchmark;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.mnist_project.util.PrimitiveStructures.getZeroMatrix;

@Getter
@Setter
@NoArgsConstructor
public class ConfusionMatrix {

    private int[][] matrix = getZeroMatrix();

    public void increment(int i, int j) {
        matrix[i][j]++;
    }

    public int getTruePositivesOfClass(int currentClass) {
        validate(currentClass);
        return matrix[currentClass][currentClass];
    }

    public int getFalsePositivesOfClass(int currentClass) {
        validate(currentClass);
        int result = 0;
        for (int i = 0; i < matrix[currentClass].length; i++) {
            if (i != currentClass) {
                result += matrix[currentClass][i];
            }
        }
        return result;
    }

    public int getFalseNegativesOfClass(int currentClass) {
        validate(currentClass);
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (i != currentClass) {
                result += matrix[i][currentClass];
            }
        }
        return result;
    }

    public int getTrueNegativesOfClass(int currentClass) {
        validate(currentClass);
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i != currentClass && j != currentClass) {
                    result += matrix[i][j];
                }
            }
        }
        return result;
    }

    public Metric getBasicMetricsOfClass(int currentClass) {
        return new BasicMetrics(getTruePositivesOfClass(currentClass),
                    getTrueNegativesOfClass(currentClass),
                    getFalsePositivesOfClass(currentClass),
                    getFalseNegativesOfClass(currentClass));
    }

    private void validate(int currentClass) {
        if (currentClass > matrix.length || currentClass < 0) {
            throw new RuntimeException("This MNIST has only 9 classes, from 0 to 9.");
        }
    }

}
