package com.example.mnist_project.config;

import com.example.mnist_project.models.neural_networks.NeuralNetworkModel;
import com.example.mnist_project.models.neural_networks.algorithm.NeuralNetwork;
import com.example.mnist_project.models.neural_networks.algorithm.NeuralNetwork.NeuralNetworkBuilder;
import com.example.mnist_project.models.neural_networks.algorithm.layers.implementation.NeuralNetworkDenseLayer;
import com.example.mnist_project.util.ActivationFunction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Configuration
public class NeuralNetworkConfig {

    private static final String NN_DIR = "neuralNetwork/";

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public NeuralNetworkModel neuralNetworkModel() throws IOException {
        return new NeuralNetworkModel(loadNeuralNetwork());
    }

    private NeuralNetwork loadNeuralNetwork() throws IOException {
        int index = 0;
        NeuralNetworkBuilder builder = NeuralNetwork.builder();
        while (true) {
            File kernelFile = new File(String.format(NN_DIR + "kernel/kernel%d.json", index));
            if (!kernelFile.exists()) {
                break;
            }
            List<List<Double>> kernel = (List<List<Double>>) objectMapper.readValue(kernelFile, List.class);
            File biasFile = new File(String.format(NN_DIR + "bias/bias%d.json", index));
            if (!biasFile.exists()) {
                break;
            }
            List<Double> bias = (List<Double>) objectMapper.readValue(biasFile, List.class);
            File activationFile = new File(String.format(NN_DIR + "activation/activation%d.txt", index));
            if (!activationFile.exists()) {
                break;
            }
            ActivationFunction activationFunction = objectMapper.readValue(activationFile, ActivationFunction.class);
            builder.addLayer(new NeuralNetworkDenseLayer(kernel, bias, activationFunction));
            index++;
        }
        return builder.build();
    }

}
