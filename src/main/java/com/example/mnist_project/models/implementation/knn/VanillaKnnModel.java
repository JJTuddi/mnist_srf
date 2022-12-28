package com.example.mnist_project.models.implementation.knn;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import com.example.mnist_project.util.DatasetReader;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

import static com.example.mnist_project.util.Constants.bestK;

@Slf4j
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
                .images(DatasetReader.getTrainingImages())
                .build();
    }

    @Override
    public Integer predict(final Mat image) {
        long start = System.nanoTime();
        Integer result = knnAlgorithm.predict(image);
        long stop = System.nanoTime();
        log.info("VanillaKnn took {} milliseconds.", (stop - start) / 1e6);
        return result;
    }

    @Override
    public ModelType getType() {
        return ModelType.VANILLA_KNN;
    }

}
