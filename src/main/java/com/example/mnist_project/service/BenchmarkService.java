package com.example.mnist_project.service;

import com.example.mnist_project.benchmark.BenchmarkResults;
import com.example.mnist_project.benchmark.ConfusionMatrix;
import com.example.mnist_project.models.Model;
import com.example.mnist_project.util.DatasetIterator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.mnist_project.util.Constants.numberOfClasses;

@Slf4j
@Service
@AllArgsConstructor
public class BenchmarkService {

    private List<Model> models;

    public List<BenchmarkResults> benchmark() {
        return models.stream().parallel()
                .map(this::getModelBenchmark)
                .collect(Collectors.toList());
    }

    public BenchmarkResults getModelBenchmark(Model model) {
        long start = System.nanoTime();
        log.info("Started to benchmark the model={}.", model.getType());
        ConfusionMatrix confusionMatrix = new ConfusionMatrix();
        IntStream.range(0, numberOfClasses).parallel().boxed().forEach(currentClass -> {
            for (Mat testImage: DatasetIterator.getTestDatasetOfClass(currentClass)) {
                int predictedClass = model.predict(testImage);
                confusionMatrix.increment(currentClass, predictedClass);
            }
        });
        log.info("Benchmark ended for model={}, and it took {} milliseconds.", model.getType(), (System.nanoTime() - start) / 1e6);
        return BenchmarkResults.builder()
                .type(model.getType())
                .confusionMatrix(confusionMatrix)
                .build();
    }

}
