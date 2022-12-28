package com.example.mnist_project.models.bayesian;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import com.example.mnist_project.models.bayesian.algorithm.NaiveBayesAlgorithm;
import com.example.mnist_project.util.DatasetIterator;
import org.opencv.core.Mat;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.mnist_project.models.ModelType.BAYESIAN_BAGGING;
import static com.example.mnist_project.util.Constants.*;


public class BayesianBaggingModel implements Model, Serializable {
    private final int s;
    private List<NaiveBayesAlgorithm> bayesians;

    public BayesianBaggingModel() {
        this(bestSForBayesianBagging);
    }

    public BayesianBaggingModel(int s) {
        this.s = s;
        train();
    }

    @Override
    public void train() {
        bayesians = IntStream.range(0, maxInEachTraining / s).parallel().boxed()
                .map(index -> new NaiveBayesAlgorithm(DatasetIterator.of(index * s, (index + 1) * s)))
                .collect(Collectors.toList());
    }

    @Override
    public Integer predict(final Mat image) {
        List<Integer> predictions = Collections.synchronizedList(IntStream.range(0, numberOfClasses).boxed().map(value -> 0).collect(Collectors.toList()));
        bayesians.parallelStream().forEach(bayesianAlgorithm -> {
            int prediction = bayesianAlgorithm.predict(image);
            predictions.set(prediction, predictions.get(prediction) + 1);
        });

        int maxLabel = 0;
        int max = predictions.get(0);
        for (int i = 1; i < numberOfClasses; i++) {
            if (max < predictions.get(i)) {
                maxLabel = i;
                max = predictions.get(i);
            }
        }
        return maxLabel;
    }

    @Override
    public ModelType getType() {
        return BAYESIAN_BAGGING;
    }

}
