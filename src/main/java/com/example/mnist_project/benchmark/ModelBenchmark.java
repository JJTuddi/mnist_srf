package com.example.mnist_project.benchmark;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.ModelType;
import com.example.mnist_project.util.Watch;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;

@Slf4j
public class ModelBenchmark implements Model {

    private Model model;

    public ModelBenchmark(Model model) {
        this.model = model;
    }

    @Override
    public void train() {
        this.model.train();
    }

    @Override
    public Integer predict(Mat image) {
        Watch watch = new Watch().start();
        Integer predicted = model.predict(image);
        log.info("Model={} took {} milliseconds to predict.", getType(), watch.stopAndGet());
        return predicted;
    }

    @Override
    public ModelType getType() {
        return model.getType();
    }

}
