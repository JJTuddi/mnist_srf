package com.example.mnist_project.components;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.example.mnist_project.util.Constants.treshold;

@Component
public class GravityCenter implements Serializable {

    public Mat center(Mat image) {
        Mat result = Mat.zeros(image.size(), image.type());
        Point displacement = getDisplacement(image);
        for (int i = 0; i < image.size().height; i++) {
            for (int j = 0; j < image.size().width; j++) {
                if (grayPixelIsEligible(image.get(i, j))) {
                    result.put((int) (i + displacement.x), (int) (j + displacement.y), image.get(i, j));
                }
            }
        }
        return result;
    }

    public Point getGravityCenter(Mat image) {
        int xPointsSum = 0, yPointsSum = 0;
        int pointsCount = 0;
        for (int i = 0; i < image.size().height; i++) {
            for (int j = 0; j < image.size().width; j++) {
                if (grayPixelIsEligible(image.get(i, j))) {
                    xPointsSum += i;
                    yPointsSum += j;
                    pointsCount++;
                }
            }
        }
        if (pointsCount == 0) {
            return new Point((int) (image.size().height / 2), (int) (image.size().width / 2));
        }
        return new Point(xPointsSum / pointsCount, yPointsSum / pointsCount);
    }

    public Point getDisplacement(Mat image) {
        Point gravityCenter = getGravityCenter(image);
        int xDisplacement = (int) Math.floor(image.size().height / 2 - gravityCenter.x),
                yDisplacement = (int) Math.floor(image.size().width / 2 - gravityCenter.y);
        return new Point(xDisplacement, yDisplacement);
    }

    public boolean grayPixelIsEligible(double[] pixel) {
        return pixel[0] >= treshold;
    }

}
