package com.example.mnist_project.models.implementation.knn;

import lombok.experimental.SuperBuilder;
import org.opencv.core.Mat;

import java.io.Serializable;
import java.util.List;

@SuperBuilder
public abstract class KnnAlgorithm implements Serializable {

    private long k;
    private List<List<Mat>> images;
    private int s = 1000;

    public int getS() {
        if (s == 0) {
            s = 1000;
        }
        return s;
    }

    public long getK() {
        if (k == 0L) {
            k = 150L;
        }
        return k;
    }

    public abstract int predict(final Mat toPredict);

    protected double computeDistance(Mat from, Mat to) {
        double result = 0;
        for (int i = 0; i < from.size().height; i++) {
            for (int j = 0; j < from.size().width; j++) {
                result += getDistanceBetweenPixels(from.get(i, j), to.get(i, j));
            }
        }
        return result;
    }

    protected double getDistanceBetweenPixels(double[] from, double[] to) {
        return Math.abs(from[0] - to[0]);
    }

    protected List<Mat> getElementsOfCurrentClass(int currentClass) {
        return images.get(currentClass).subList(0, Math.min(getS(), images.get(currentClass).size()));
    }

    public static int getLabelFromPredictions(List<Double> predictions) {
        double maxi = predictions.get(0);
        int maxLabel = 0;
        for (int i = 1; i < predictions.size(); i++) {
            if (maxi < predictions.get(i)) {
                maxi = predictions.get(i);
                maxLabel = i;
            }
        }

        return maxLabel;
    }

}
