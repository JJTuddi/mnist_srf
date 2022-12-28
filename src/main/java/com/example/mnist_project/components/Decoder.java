package com.example.mnist_project.components;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Base64;

@Component
public class Decoder implements Serializable {

    public Mat base64StringToImage(String base64StringImage) {
        byte[] bytes = Base64.getDecoder().decode(base64StringImage);
        return Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_GRAYSCALE);
    }

}
