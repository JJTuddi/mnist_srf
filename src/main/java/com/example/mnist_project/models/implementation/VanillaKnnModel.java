package com.example.mnist_project.models.implementation;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import com.example.mnist_project.util.DatasetReader;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class VanillaKnnModel implements Model {

    private KnnAlgorithm knnAlgorithm;
    private Long k = 150L;

    public VanillaKnnModel() {
        train();
    }

    public VanillaKnnModel(Long k) {
        this.k = k;
        train();
    }

    @Override
    public void train() {
        knnAlgorithm = KnnAlgorithm.builder()
                .k(k)
                .images(DatasetReader.getTrainingImages().stream().map(list -> list.subList(0, 1000)).collect(Collectors.toList()))
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
