package com.example.mnist_project.models.implementation;

import com.example.mnist_project.benchmark.BenchmarkResults;
import com.example.mnist_project.models.implementation.knn.VanillaKnnModel;
import com.example.mnist_project.service.BenchmarkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
class VanillaKnnModelTest {

    static {
        System.load("D:\\Tuddi\\Desktop\\MNIST_faculta\\opencv\\build\\java\\x64\\opencv_java460.dll");
    }

    private static final String template = "bestk_%s.fit.json";

    private final BenchmarkService benchmarkService = new BenchmarkService(null);

    private final ObjectMapper objectMapper = new ObjectMapper();

    // @Test
    void findBestK() throws JsonProcessingException {
        List<Long> ks = List.of(10L, 30L, 50L, 70L, 100L, 120L, 150L, 170L, 200L, 220L, 250L, 270L, 300L, 320L, 350L, 370L, 400L);
        ks.parallelStream()
                .forEach(k -> {
                    BestKFit bestFit = BestKFit.builder()
                            .k(k)
                            .benchmarkResults(benchmarkService.getModelBenchmark(new VanillaKnnModel(k)))
                            .build();
                    try {
                        writeResultToFile(k, bestFit);
                    } catch (IOException e) {
                        log.error("Failed for k={}.", k);
                    }
                });
    }

    private void writeResultToFile(Long k, BestKFit bestKFit) throws IOException {
        File file = new File(String.format(template, k.toString()));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bestKFit));
        writer.flush();
        writer.close();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class BestKFit {

        private Long k;
        private BenchmarkResults benchmarkResults;

    }

}