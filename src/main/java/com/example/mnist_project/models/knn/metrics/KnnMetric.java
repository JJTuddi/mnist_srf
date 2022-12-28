package com.example.mnist_project.models.knn.metrics;

import org.opencv.core.Mat;

import static com.example.mnist_project.util.PixelHelper.isPixelActive;

public enum KnnMetric {

    LP1((from, to) -> {
        double result = 0;
        for (int i = 0; i < from.size().height; i++) {
            for (int j = 0; j < from.size().width; j++) {
                result += Math.abs(from.get(i, j)[0] - to.get(i, j)[0]);
            }
        }
        return result;
    }), LP2((from, to) -> {
        double result = 0;
        for (int i = 0; i < from.size().height; i++) {
            for (int j = 0; j < from.size().width; j++) {
                result += Math.pow(from.get(i, j)[0] - to.get(i, j)[0], 2);
            }
        }
        return Math.sqrt(result);
    }), DistanceTransformScore((image, distanceTransform) -> {
        int count = 0;
        double sum = 0;
        int height = (int) image.size().height;
        int width = (int) image.size().width;
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (isPixelActive(image.get(i, j))) {
                    count++;
                    sum += distanceTransform.get(i, j)[0];
                }
            }
        }
        return sum / count;
    });

    private ImageMetric metric;

    KnnMetric(ImageMetric metric) {
        this.metric = metric;
    }

    public double compute(Mat from, Mat to) {
        return metric.compute(from, to);
    }

    private interface ImageMetric {

        double compute(Mat from, Mat to);

    }

}
