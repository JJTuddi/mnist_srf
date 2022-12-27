package com.example.mnist_project.models.implementation;

import com.example.mnist_project.util.KnnPair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.opencv.core.Mat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static com.example.mnist_project.util.Constants.numberOfClasses;

@Builder
@AllArgsConstructor
public class KnnAlgorithm {

    private final long k;
    private final List<List<Mat>> images;

    public int predict(final Mat toPredict) {
        List<KnnPair> pairs = IntStream.range(0, numberOfClasses)
                .boxed()
                .flatMap(currentClass -> images.get(currentClass)
                        .parallelStream()
                        .map(image -> KnnPair.builder()
                                .currentClass(currentClass)
                                .distance(computeDistance(toPredict, image))
                                .build()))
                .sorted(Comparator.comparing(KnnPair::getDistance))
                .limit(k)
                .collect(Collectors.toList());

        List<Double> predictions = DoubleStream.generate(() -> 0.0)
                .boxed()
                .limit(numberOfClasses)
                .collect(Collectors.toList());

        for (KnnPair pair: pairs) {
            predictions.set(pair.getCurrentClass(), predictions.get(pair.getCurrentClass()) + 1);
        }

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

    private double computeDistance(Mat from, Mat to) {
        double result = 0;
        for (int i = 0; i < from.size().height; i++) {
            for (int j = 0; j < from.size().width; j++) {
                result += getDistanceBetweenPixels(from.get(i, j), to.get(i, j));
            }
        }
        return result;
    }

    private double getDistanceBetweenPixels(double[] from, double[] to) {
        return Math.abs(from[0] - to[0]);
    }

}
