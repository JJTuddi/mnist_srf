package com.example.mnist_project.components;

import lombok.AllArgsConstructor;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

import static com.example.mnist_project.util.Constants.imageHeight;
import static com.example.mnist_project.util.Constants.imageWidth;
import static com.example.mnist_project.util.PrimitiveStructures.*;

@Component
@AllArgsConstructor
public class DistanceTransform implements Serializable {

    private ImageHelper imageHelper;

    public Mat computeAverageDistanceTransformOfImages(List<Mat> images) {
        if (images.isEmpty()) {
            return Mat.zeros(imageHeight, imageWidth, CvType.CV_8UC1);
        }
        double[][] matrix = images.parallelStream()
                .map(imageHelper::negateImage)
                .map(image -> imageToMatrix(computeDistanceTransform(image, 7, 5)))
                .reduce(getZeroMatrixImage(), (acc, crt) -> {
                    for (int i = 0; i < imageHeight; i++) {
                        for (int j = 0; j < imageWidth; j++) {
                            acc[i][j] = acc[i][j] + crt[i][j] / images.size();
                        }
                    }
                    return acc;
                });
        return matrixToImage(matrix);
    }

    private Mat computeDistanceTransform(Mat image, double diagonal, double normal) {
        Mat dtResult = image.clone();
        double pixel;
        int height = (int) image.size().height, width = (int) image.size().width;
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                pixel = dtResult.get(i, j)[0];
                pixel = Math.min(pixel, dtResult.get(i - 1, j - 1)[0] + diagonal);
                pixel = Math.min(pixel, dtResult.get(i - 1, j)[0] + normal);
                pixel = Math.min(pixel, dtResult.get(i - 1, j + 1)[0] + diagonal);
                pixel = Math.min(pixel, dtResult.get(i, j - 1)[0] + normal);
                dtResult.put(i, j, pixel);
            }
        }
        for (int i = height - 2; i > 0; i--) {
            for (int j = width - 2; j > 0; j--) {
                pixel = dtResult.get(i, j)[0];
                pixel = Math.min(pixel, dtResult.get(i, j + 1)[0] + normal);
                pixel = Math.min(pixel, dtResult.get(i + 1, j)[0] + normal);
                pixel = Math.min(pixel, dtResult.get(i + 1, j + 1)[0] + diagonal);
                pixel = Math.min(pixel, dtResult.get(i + 1, j - 1)[0] + diagonal);
                dtResult.put(i, j, pixel);
            }
        }
        return dtResult;
    }

}
