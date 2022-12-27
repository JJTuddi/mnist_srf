package com.example.mnist_project.util;

import lombok.NoArgsConstructor;

import static com.example.mnist_project.util.Constants.treshold;
import static lombok.AccessLevel.NONE;

@NoArgsConstructor(access = NONE)
public class PixelHelper {

    public static boolean isPixelActive(double[] pixel) {
        return pixel[0] > treshold;
    }

    public static Integer getPixelValue(double[] pixel) {
        return pixel[0] > treshold ? 1 : 0;
    }

}
