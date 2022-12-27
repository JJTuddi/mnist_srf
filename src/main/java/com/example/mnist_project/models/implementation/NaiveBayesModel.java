package com.example.mnist_project.models.implementation;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import com.example.mnist_project.util.DatasetIterator;
import com.example.mnist_project.util.MnistPair;
import com.example.mnist_project.util.PixelHelper;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static com.example.mnist_project.util.Constants.imageSize;
import static com.example.mnist_project.util.Constants.numberOfClasses;

@Component
public class NaiveBayesModel implements Model {

    private List<Double> posteriorProbability = DoubleStream.generate(() -> 0.0)
            .boxed()
            .limit(numberOfClasses)
            .collect(Collectors.toList());
    private List<List<Double>> likelyhoods;
    private AtomicInteger totalImages = new AtomicInteger(0);

    public NaiveBayesModel() {
        train();
    }

    @Override
    public void train() {
        likelyhoods = IntStream.range(0, numberOfClasses)
                .parallel()
                .boxed()
                .map(this::computeLikelyhoodOfAClass)
                .collect(Collectors.toList());
        posteriorProbability = posteriorProbability.stream()
                .map(p -> Math.log10(p / totalImages.get()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer predict(Mat image) {
        return IntStream.range(0, numberOfClasses).parallel().boxed().map(currentClass -> {
            int height = (int) image.size().height;
            int width = (int) image.size().width;
            double probability = posteriorProbability.get(currentClass);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int index = i * height + j;
                    if (PixelHelper.isPixelActive(image.get(i, j))) {
                        probability += likelyhoods.get(currentClass).get(index);
                    }
                }
            }
            return MnistPair.builder().currentClass(currentClass).probability(-probability).build();
        }).sorted(Comparator.comparing(MnistPair::getProbability)).collect(Collectors.toList()).get(0).getCurrentClass();
    }

    @Override
    public ModelType getType() {
        return ModelType.NAIVE_BAYESIAN;
    }

    private void updateLikelyhoodProbabilities(List<Double> likelyhoodProbabilities, Mat image) {
        int height = (int) image.size().height;
        int width = (int) image.size().width;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = i * height + j;
                double previousValue = likelyhoodProbabilities.get(index);
                likelyhoodProbabilities.set(index, previousValue + PixelHelper.getPixelValue(image.get(i, j)));
            }
        }
    }

    private List<Double> computeLikelyhoodOfAClass(Integer currentClass) {
        List<Double> likelyhoodProbability = DoubleStream.generate(() -> 1.0)
                .boxed()
                .limit(imageSize)
                .collect(Collectors.toList());
        int counter = 0;
        for (Mat image: DatasetIterator.getTrainingDatasetOfClass(currentClass)) {
            counter++;
            updateLikelyhoodProbabilities(likelyhoodProbability, image);
            posteriorProbability.set(currentClass, posteriorProbability.get(currentClass) + 1);
        }
        totalImages.addAndGet(counter);
        final Integer numberOfElements = counter;
        likelyhoodProbability.replaceAll(probability -> Math.log10(probability / numberOfElements));
        return likelyhoodProbability;
    }

}
