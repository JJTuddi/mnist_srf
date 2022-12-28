package com.example.mnist_project.util;

import lombok.NoArgsConstructor;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static com.example.mnist_project.util.Constants.*;
import static lombok.AccessLevel.NONE;

@NoArgsConstructor(access = NONE)
public class PrimitiveStructures {

    public static int[][] getZeroMatrix() {
        int[][] result = new int[numberOfClasses][numberOfClasses];
        for (int i = 0; i < numberOfClasses; i++) {
            for (int j = 0; j < numberOfClasses; j++) {
                result[i][j] = 0;
            }
        }
        return result;
    }

    public static double[][] getZeroMatrixImage() {
        double[][] result = new double[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                result[i][j] = 0;
            }
        }
        return result;
    }

    public static double[][] imageToMatrix(Mat image) {
        double[][] result = new double[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                result[i][j] = image.get(i, j)[0];
            }
        }
        return result;
    }

    public static Mat matrixToImage(double[][] matrix) {
        Mat result = Mat.zeros(imageHeight, imageWidth, CvType.CV_8UC1);
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                result.put(i, j, matrix[i][j]);
            }
        }
        return result;
    }

}
