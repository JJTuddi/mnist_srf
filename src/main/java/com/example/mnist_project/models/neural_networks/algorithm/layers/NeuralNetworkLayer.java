package com.example.mnist_project.models.neural_networks.algorithm.layers;

import java.util.List;

public interface NeuralNetworkLayer {

    List<Double> compute(List<Double> input);

}
