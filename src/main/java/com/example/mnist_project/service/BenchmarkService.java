package com.example.mnist_project.service;

import com.example.mnist_project.benchmark.BenchmarkResults;
import com.example.mnist_project.benchmark.ConfusionMatrix;
import com.example.mnist_project.benchmark.ModelBenchmark;
import com.example.mnist_project.models.Model;
import com.example.mnist_project.util.DatasetIterator;
import com.example.mnist_project.util.Watch;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.mnist_project.util.Constants.numberOfClasses;

@Slf4j
@Service
public class BenchmarkService {

    private List<Model> models;

    public BenchmarkService(List<Model> models) {
        this.models = models.stream()
                .map(ModelBenchmark::new)
                .collect(Collectors.toList());
    }

    public List<BenchmarkResults> benchmark() {
        return models.stream()
                .map(this::getModelBenchmark)
                .collect(Collectors.toList());
    }

    public BenchmarkResults getModelBenchmark(Model model) {
        Watch watch = new Watch().start();
        log.info("Started to benchmark the model={}.", model.getType());
        ConfusionMatrix confusionMatrix = new ConfusionMatrix();
        IntStream.range(0, numberOfClasses).parallel().boxed().forEach(currentClass -> {
            for (Mat testImage : DatasetIterator.of().getTestDataOfClass(currentClass)) {
                int predictedClass = model.predict(testImage);
                confusionMatrix.increment(currentClass, predictedClass);
            }
        });
        log.info("Benchmark ended for model={}, and it took {} milliseconds.", model.getType(), watch.stopAndGet());
        return BenchmarkResults.builder()
                .type(model.getType())
                .confusionMatrix(confusionMatrix)
                .build();
    }

}
