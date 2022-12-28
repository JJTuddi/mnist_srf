package com.example.mnist_project.models.implementation;

import com.example.mnist_project.benchmark.BenchmarkResults;
import com.example.mnist_project.models.bayesian.BayesianBaggingModel;
import com.example.mnist_project.service.BenchmarkService;
import com.example.mnist_project.util.DatasetIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class BayesianBaggingModelTest {

    static {
        System.load("C:\\Users\\blideatudorel\\IdeaProjects\\ggg\\mnist_project\\opencv\\build\\java\\x64\\opencv_java460.dll");
    }

    // @Test
    void findBestAccuracy() throws Exception {
        BenchmarkService benchmarkService = new BenchmarkService(Collections.emptyList());
        log.info("Started to find the best naive bayesian bagging...");
        List<BenchmarkPair> benchmark = IntStream.range(1, 220).parallel().boxed().map(s -> {
            BenchmarkResults results = benchmarkService.getModelBenchmark(new BayesianBaggingModel(s * 25));
            System.out.println("Done for " + (s * 25));
            return BenchmarkPair.builder().s(s * 25).score(results.getOverallAccuracy()).benchmark(results).build();
        }).sorted(Comparator.comparing(BenchmarkPair::getScore)).collect(Collectors.toList());
        writeToFile(benchmark);
    }

    @Test
    void seeIfIsWorking() {
        List<Mat> ofClass0 = (List<Mat>) DatasetIterator.all().getTrainingDataOfClass(0);
        assertEquals(5923, ofClass0.size());
    }

    private void writeToFile(List<BenchmarkPair> benchmark) throws Exception {
        File file = new File("bayesian_bagging_results.json");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(benchmark));
        writer.flush();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static class BenchmarkPair {
        private int s;
        private double score;
        private BenchmarkResults benchmark;
    }

}