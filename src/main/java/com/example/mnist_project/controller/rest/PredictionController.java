package com.example.mnist_project.controller.rest;

import com.example.mnist_project.models.ModelType;
import com.example.mnist_project.service.PredictionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/predict")
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class PredictionController {

    private PredictionService predictionService;

    @PostMapping
    public Map<ModelType, Integer> predict(@RequestBody String stringImage) throws IOException, ClassNotFoundException {
        return predictionService.predict(stringImage);
    }

}
