package com.example.mnist_project.components;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.List;

import static com.example.mnist_project.util.Constants.imageHeight;
import static com.example.mnist_project.util.Constants.imageWidth;
import static com.example.mnist_project.util.PixelHelper.isPixelActive;

public class DistanceTransform {

    private Mat distanceTransform;
    private ImageHelper imageHelper = new ImageHelper(new Decoder(), new GravityCenter());

    public DistanceTransform(List<Mat> images) {
        computeAverageDistanceTransformOfImages(images);
    }

    private void computeAverageDistanceTransformOfImages(List<Mat> images) {
        if (images.isEmpty()) {
            distanceTransform = Mat.zeros(imageHeight, imageWidth, CvType.CV_8UC1);
            return;
        }
        distanceTransform = images.parallelStream()
                .map(imageHelper::negateImage)
                .map(image -> computeDistanceTransform(image, 7, 5))
                .reduce(Mat.zeros(imageHeight, imageWidth, CvType.CV_8UC1), (acc, crt) -> {
                    for (int i = 0; i < imageHeight; i++) {
                        for (int j = 0; j < imageWidth; j++) {
                            acc.put(i, j, acc.get(i, j)[0] + (crt.get(i, j)[0] / images.size()));
                        }
                    }
                    return acc;
                });
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

    public double computeScore(Mat image) {
        int count = 0;
        double sum = 0;
        int height = (int) image.size().height;
        int width = (int) image.size().width;
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (isPixelActive(image.get(i, j))) {
                    count++;
                    sum += distanceTransform.get(i, j)[0];
                }
            }
        }
        return sum / count;
    }

}
