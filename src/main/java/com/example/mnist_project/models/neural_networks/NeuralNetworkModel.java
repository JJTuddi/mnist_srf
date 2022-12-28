package com.example.mnist_project.models.neural_networks;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import com.example.mnist_project.models.neural_networks.algorithm.NeuralNetwork;
import lombok.Getter;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

import static com.example.mnist_project.util.Constants.*;

@Getter
public class NeuralNetworkModel implements Model {

    private final NeuralNetwork neuralNetwork;

    public NeuralNetworkModel(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        train();
    }

    @Override
    public void train() {}

    @Override
    public Integer predict(Mat image) {
        List<Double> predictions = neuralNetwork.compute(imageToListOfDouble(image));

        int maxLabel = 0;
        double max = predictions.get(maxLabel);
        for (int i = 1; i < predictions.size(); i++) {
            if (max < predictions.get(i)) {
                max = predictions.get(i);
                maxLabel = i;
            }
        }
        return maxLabel;
    }

    @Override
    public ModelType getType() {
        return ModelType.NEURAL_NETWORK;
    }

    private List<Double> imageToListOfDouble(Mat image) {
        List<Double> result = new ArrayList<>(imageSize);
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                result.add(image.get(i, j)[0]);
            }
        }
        return result;
    }

}
