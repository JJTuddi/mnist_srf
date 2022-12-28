package com.example.mnist_project.models.knn;

import com.example.mnist_project.components.DistanceTransform;
import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import com.example.mnist_project.models.knn.algorithm.KnnAlgorithm;
import com.example.mnist_project.models.knn.algorithm.KnnAlgorithmImpl;
import com.example.mnist_project.util.DatasetIterator;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.mnist_project.models.knn.metrics.KnnMetric.DistanceTransformScore;
import static com.example.mnist_project.util.Constants.*;

@Component
public class KnnAndDistanceTransformModel implements Model {

    private final DistanceTransform distanceTransform;
    private KnnAlgorithm knnAlgorithm;

    public KnnAndDistanceTransformModel(DistanceTransform distanceTransform) {
        this.distanceTransform = distanceTransform;
        train();
    }

    @Override
    public void train() {
        knnAlgorithm = KnnAlgorithmImpl.builder()
                .k(10L)
                .metric(DistanceTransformScore)
                .images(prepareDistanceTransformImages())
                .build();
    }

    @Override
    public Integer predict(final Mat image) {
        return knnAlgorithm.predict(image);
    }

    @Override
    public ModelType getType() {
        return ModelType.KNN_AND_DISTANCE_TRANSFORM;
    }

    private List<List<Mat>> prepareDistanceTransformImages() {
        DatasetIterator datasetIterator = DatasetIterator.all();
        return IntStream.range(0, numberOfClasses).boxed().map(currentClass -> {
            List<Mat> images = (List<Mat>) datasetIterator.getTrainingDataOfClass(currentClass);
            return IntStream.range(0, maxInEachTraining / bestDistanceTransformSplitting).parallel().boxed()
                    .map(i -> {
                        List<Mat> split = images.subList(i * bestDistanceTransformSplitting, (i + 1) * bestDistanceTransformSplitting);
                        return distanceTransform.computeAverageDistanceTransformOfImages(split);
                    }).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }

}
