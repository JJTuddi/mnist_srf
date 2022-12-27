package com.example.mnist_project.service;

import com.example.mnist_project.components.Decoder;
import com.example.mnist_project.components.GravityCenter;
import com.example.mnist_project.components.ImageHelper;
import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PredictionService {

    private Decoder decoder;
    private List<Model> models;
    private ImageHelper imageHelper;

    public Map<ModelType, Integer> predict(String stringImage) {
        Mat image = imageHelper.centerImage(imageHelper.negateImage(imageHelper.decodeImageFromBase64Encoding(stringImage)));
        Map<ModelType, Integer> predictions = models.stream()
                .collect(Collectors.toMap(Model::getType, model -> model.predict(image)));
        logPredictions(predictions);
        return predictions;
    }

    private void logPredictions(Map<ModelType, Integer> result) {
        log.info("Predicted digits are: " + result.entrySet().stream()
                .map(entry -> String.format("%s=%d", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(","))
        );
    }

}
