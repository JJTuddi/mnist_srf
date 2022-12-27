package com.example.mnist_project.benchmark;

import lombok.Getter;

@Getter
public class BasicMetrics implements Metric {

    private final double accuracy;
    private final double precision;
    private final double recall;
    private final double f1;

    public BasicMetrics(int truePositives, int trueNegatives, int falsePositives, int falseNegatives) {
        accuracy = 1.0 * (truePositives + trueNegatives) / (truePositives + trueNegatives + falsePositives + falseNegatives);
        precision = 1.0 * truePositives / (truePositives + falsePositives);
        recall = 1.0 * truePositives / (truePositives + falseNegatives);
        f1 = 2 * precision * recall / (precision + recall);
    }

}
