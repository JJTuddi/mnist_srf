package com.example.mnist_project.models;

import org.opencv.core.Mat;

public interface Model {

    void train();
    Integer predict(Mat image);
    ModelType getType();

}
