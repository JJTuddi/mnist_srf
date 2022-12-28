package com.example.mnist_project.models.knn.algorithm;

import com.example.mnist_project.models.knn.metrics.KnnMetric;
import lombok.experimental.SuperBuilder;
import org.opencv.core.Mat;

import java.io.Serializable;
import java.util.List;

import static com.example.mnist_project.util.Constants.bestK;

@SuperBuilder
public abstract class KnnAlgorithm implements Serializable {

    private int s;
    private long k;
    private KnnMetric metric;
    private List<List<Mat>> images;

    public int getS() {
        if (s == 0) {
            s = 1000;
        }
        return s;
    }

    public long getK() {
        if (k == 0L) {
            k = bestK;
        }
        return k;
    }

    public abstract int predict(final Mat toPredict);

    protected double computeDistance(Mat from, Mat to) {
        if (metric == null) {
            metric = KnnMetric.LP1;
        }
        return metric.compute(from, to);
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
