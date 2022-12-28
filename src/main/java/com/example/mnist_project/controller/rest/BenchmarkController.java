package com.example.mnist_project.controller.rest;

import com.example.mnist_project.benchmark.BenchmarkResults;
import com.example.mnist_project.service.BenchmarkService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/benchmark")
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class BenchmarkController {

    private BenchmarkService benchmarkService;

    @GetMapping
    public List<BenchmarkResults> getBenchmark() {
        return benchmarkService.benchmark();
    }

}
