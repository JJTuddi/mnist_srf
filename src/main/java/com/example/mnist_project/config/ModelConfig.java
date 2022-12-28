package com.example.mnist_project.config;

import com.example.mnist_project.models.Model;
import com.example.mnist_project.models.bayesian.BayesianBaggingModel;
import com.example.mnist_project.models.bayesian.NaiveBayesModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Configuration
public class ModelConfig {

    private static String modelFileNameTemplate = "serializedModels/%s";

    @Bean
    public Model bayesianBagging() throws IOException, ClassNotFoundException {
        String modelName = "bayesianBagging";
        if (modelIsSerialized(modelName)) {
            return loadModel(modelName);
        }
        Model model = new BayesianBaggingModel();
        saveModel(modelName, model);
        return model;
    }

    @Bean
    public Model naiveBayesModel() throws IOException, ClassNotFoundException {
        String modelName = "naiveBayesModel";
        if (modelIsSerialized(modelName)) {
            return loadModel(modelName);
        }
        Model model = new NaiveBayesModel();
        saveModel(modelName, model);
        return model;
    }

    private boolean modelIsSerialized(String modelName) {
        return getModelFile(modelName).exists();
    }

    private void saveModel(String modelName, Model model) throws IOException {
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(getModelFile(modelName)));
        writer.writeObject(model);
        writer.flush();
    }

    private Model loadModel(String modelName) throws IOException, ClassNotFoundException {
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(getModelFile(modelName)));
        return (Model) reader.readObject();
    }

    private File getModelFile(String modelName) {
        return new File(String.format(modelFileNameTemplate, modelName));
    }

}
