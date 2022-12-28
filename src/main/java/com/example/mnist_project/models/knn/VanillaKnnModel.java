package com.example.mnist_project.models.knn;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import com.example.mnist_project.models.knn.algorithm.KnnAlgorithm;
import com.example.mnist_project.models.knn.algorithm.KnnAlgorithmOptimized;
import com.example.mnist_project.util.DatasetReader;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

import static com.example.mnist_project.models.knn.metrics.KnnMetric.LP1;
import static com.example.mnist_project.util.Constants.bestK;


@Component
public class VanillaKnnModel implements Model {

    private KnnAlgorithm knnAlgorithm;
    private Long k = bestK;

    public VanillaKnnModel() {
        train();
    }

    public VanillaKnnModel(Long k) {
        this.k = k;
        train();
    }

    @Override
    public void train() {
        knnAlgorithm = KnnAlgorithmOptimized.builder()
                .k(k)
                .metric(LP1)
                .images(DatasetReader.getTrainingImages())
                .build();
    }

    @Override
    public Integer predict(final Mat image) {
        return knnAlgorithm.predict(image);
    }

    @Override
    public ModelType getType() {
        return ModelType.VANILLA_KNN;
    }

}
