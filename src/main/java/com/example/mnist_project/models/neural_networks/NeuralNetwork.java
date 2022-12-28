package com.example.mnist_project.models.neural_networks;


import com.example.mnist_project.models.neural_networks.layers.NeuralNetworkLayer;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public class NeuralNetwork {

    private List<NeuralNetworkLayer> layers;

    public List<Double> compute(List<Double> input) {
        List<Double> result = input;
        for (NeuralNetworkLayer layer : layers) {
            result = layer.compute(result);
        }
        return result;
    }

    public static NeuralNetworkBuilder builder() {
        return new NeuralNetworkBuilder();
    }

    public static class NeuralNetworkBuilder {

        List<NeuralNetworkLayer> layers = new LinkedList<>();

        public NeuralNetworkBuilder addLayer(NeuralNetworkLayer layer) {
            layers.add(layer);
            return this;
        }

        public NeuralNetwork build() {
            return new NeuralNetwork(layers);
        }

    }

}
