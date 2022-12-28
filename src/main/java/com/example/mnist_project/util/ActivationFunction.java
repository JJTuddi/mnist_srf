package com.example.mnist_project.util;

import java.util.function.Function;

public enum ActivationFunction {

    ReLU(value -> Math.max(0,value)),

    NONE(value ->value);

    private Function<Double, Double> function;

    ActivationFunction(Function<Double, Double> function) {
        this.function = function;
    }

    public Double apply(Double value) {
        return function.apply(value);
    }

}
