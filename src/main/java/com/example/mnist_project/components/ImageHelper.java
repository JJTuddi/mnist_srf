package com.example.mnist_project.components;

import lombok.AllArgsConstructor;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@AllArgsConstructor
public class ImageHelper implements Serializable {

    private Decoder decoder;
    private GravityCenter gravityCenter;

    public Mat decodeImageFromBase64Encoding(String imageAsBase64String) {
        return decoder.base64StringToImage(imageAsBase64String);
    }

    public Mat centerImage(Mat image) {
        return gravityCenter.center(image);
    }

    public Mat negateImage(Mat image) {
        Mat result = image.clone();
        for (int i = 0; i < result.size().height; i++) {
            for (int j = 0; j < result.size().width; j++) {
                result.put(i, j, getNegativeOfPixel(result.get(i, j)));
            }
        }
        return result;
    }

    private double[] getNegativeOfPixel(double[] pixel) {
        return new double[] { 255 - pixel[0] };
    }

}
