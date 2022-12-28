package com.example.mnist_project.models.implementation.bayesian;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import org.opencv.core.Mat;

import java.io.Serializable;

public class NaiveBayesModel implements Model, Serializable {

    NaiveBayesAlgorithm naiveBayesAlgorithm;

    public NaiveBayesModel() {
        train();
    }

    @Override
    public void train() {
        naiveBayesAlgorithm = new NaiveBayesAlgorithm();
    }

    @Override
    public Integer predict(Mat image) {
        return naiveBayesAlgorithm.predict(image);
    }

    @Override
    public ModelType getType() {
        return ModelType.NAIVE_BAYESIAN;
    }

}
