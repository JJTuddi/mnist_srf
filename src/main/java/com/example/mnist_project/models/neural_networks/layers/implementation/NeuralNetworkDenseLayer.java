package com.example.mnist_project.models.neural_networks.layers.implementation;

import com.example.mnist_project.models.neural_networks.layers.NeuralNetworkLayer;
import com.example.mnist_project.util.ActivationFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NeuralNetworkDenseLayer implements NeuralNetworkLayer {

    List<Double> bias;
    List<List<Double>> kernel;
    ActivationFunction activationFunction;

    public NeuralNetworkDenseLayer(List<List<Double>> kernel, List<Double> bias, ActivationFunction activationFunction) {
        this.bias = bias;
        this.kernel = kernel;
        verifyBiasAndKernel();
        this.activationFunction = activationFunction == null ? ActivationFunction.NONE : activationFunction;
    }

    @Override
    public List<Double> compute(List<Double> input) {
        List<Double> result = addBias(dotProduct(input));
        result.replaceAll(activationFunction::apply);
        return result;
    }

    private List<Double> dotProduct(List<Double> input) {
        verifyInput(input);
        List<Double> dotResult = new ArrayList<>(input.size());
        int numberOfRows = kernel.size();
        int numberOfColumns = kernel.get(0).size();
        for (int i = 0; i < numberOfColumns; i++) {
            double result = 0;
            for (int j = 0; j < numberOfRows; j++) {
                result += input.get(j) * kernel.get(j).get(i);
            }
            dotResult.add(result);
        }
        return dotResult;
    }

    private List<Double> addBias(List<Double> dotResult) {
        for (int i = 0; i < bias.size(); i++) {
            dotResult.set(i, dotResult.get(i) + bias.get(i));
        }
        return dotResult;
    }

    private void verifyInput(List<Double> input) {
        if (input == null) {
            throw new IllegalArgumentException("The input can't be null");
        }
        if (input.size() != kernel.size()) {
            throw new IllegalArgumentException("The input can't have different size from row.");
        }
    }

    private void verifyBiasAndKernel() {
        if (bias == null) {
            throw new IllegalArgumentException("Bias can't be null");
        }
        int index = 0;
        for(List<Double> kernelRow: kernel) {
            if (kernelRow == null) {
                throw new IllegalArgumentException(String.format("Kernel row(index=%d) can't be null", index));
            }
            if (kernelRow.size() != bias.size()) {
                throw new IllegalArgumentException(String.format("Kernel row(index=%d) doesn't have the same size as the bias (%d!=%d).", index, kernelRow.size(), bias.size()));
            }
            index++;
        }
    }

}
