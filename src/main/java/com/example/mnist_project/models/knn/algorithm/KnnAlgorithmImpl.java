package com.example.mnist_project.models.knn.algorithm;

import com.example.mnist_project.util.KnnPair;
import lombok.experimental.SuperBuilder;
import org.opencv.core.Mat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static com.example.mnist_project.util.Constants.numberOfClasses;

@SuperBuilder
public class KnnAlgorithmImpl extends KnnAlgorithm {

    @Override
    public int predict(final Mat toPredict) {
        List<KnnPair> pairs = IntStream.range(0, numberOfClasses)
                .boxed()
                .flatMap(currentClass -> getElementsOfCurrentClass(currentClass)
                        .parallelStream()
                        .map(image -> KnnPair.builder()
                                .currentClass(currentClass)
                                .distance(computeDistance(toPredict, image))
                                .build()))
                .sorted(Comparator.comparing(KnnPair::getDistance))
                .limit(getK())
                .collect(Collectors.toList());

        List<Double> predictions = DoubleStream.generate(() -> 0.0)
                .boxed()
                .limit(numberOfClasses)
                .collect(Collectors.toList());

        for (KnnPair pair : pairs) {
            predictions.set(pair.getCurrentClass(), predictions.get(pair.getCurrentClass()) + 1);
        }

        return getLabelFromPredictions(predictions);
    }

}
