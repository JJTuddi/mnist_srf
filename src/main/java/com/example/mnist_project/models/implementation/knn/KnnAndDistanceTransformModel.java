package com.example.mnist_project.models.implementation.knn;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import org.opencv.core.Mat;


public class KnnAndDistanceTransformModel implements Model {

    @Override
    public void train() {
    }

    @Override
    public Integer predict(final Mat image) {
        return 0;
    }

    @Override
    public ModelType getType() {
        return ModelType.KNN_AND_DISTANCE_TRANSFORM;
    }

}
