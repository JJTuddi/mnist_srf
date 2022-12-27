package com.example.mnist_project.util;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.IOException;

public class ImageHelper {

    public static void saveImageAsPng(String name, Mat image) throws IOException {
        Imgcodecs.imwrite(name + ".png", image);
    }

}
