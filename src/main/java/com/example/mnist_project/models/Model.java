package com.example.mnist_project.models;

import org.opencv.core.Mat;

import java.io.Serializable;

public interface Model extends Serializable {

    void train();

    Integer predict(Mat image);

    ModelType getType();

}
