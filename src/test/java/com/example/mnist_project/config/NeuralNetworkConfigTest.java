package com.example.mnist_project.config;

import com.example.mnist_project.util.ActivationFunction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeuralNetworkConfigTest {

    @Test
    public void testActivationFunctionDeserialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ActivationFunction identity = objectMapper.readValue("\"NONE\"", ActivationFunction.class);
        ActivationFunction relu = objectMapper.readValue("\"ReLU\"", ActivationFunction.class);
        assertNotNull(relu);
        assertNotNull(identity);
        for (int i = 0; i < 1000; i++) {
            Double randomNumber = Math.random() * 1000 * Math.signum(Math.random() - 0.3);
            assertEquals(ActivationFunction.NONE.apply(randomNumber), identity.apply(randomNumber));
            assertEquals(ActivationFunction.ReLU.apply(randomNumber), relu.apply(randomNumber));
        }
    }

}