package com.example.mnist_project.models.knn.algorithm;

import com.example.mnist_project.util.KnnPair;
import lombok.experimental.SuperBuilder;
import org.opencv.core.Mat;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static com.example.mnist_project.util.Constants.numberOfClasses;

@SuperBuilder
public class KnnAlgorithmOptimized extends KnnAlgorithm {

    @Override
    public int predict(final Mat toPredict) {
        Queue<KnnPair> priorityQueue = IntStream.range(0, numberOfClasses)
                .boxed()
                .parallel()
                .flatMap(currentClass -> getElementsOfCurrentClass(currentClass).parallelStream()
                        .map(image -> KnnPair.builder()
                                .currentClass(currentClass)
                                .distance(computeDistance(toPredict, image))
                                .build()))
                .collect(Collectors.toCollection(() -> new PriorityQueue<>(getS() * numberOfClasses, Comparator.comparing(KnnPair::getDistance))));

        List<Double> predictions = DoubleStream.generate(() -> 0.0)
                .boxed()
                .limit(numberOfClasses)
                .collect(Collectors.toList());

        for (int i = 0; i < getK(); i++) {
            KnnPair pair = priorityQueue.poll();
            if (pair == null) {
                break;
            }
            predictions.set(pair.getCurrentClass(), predictions.get(pair.getCurrentClass()) + 1);
        }

        return getLabelFromPredictions(predictions);
    }

}
