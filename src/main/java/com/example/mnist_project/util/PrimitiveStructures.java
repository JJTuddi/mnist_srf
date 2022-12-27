package com.example.mnist_project.util;

import lombok.NoArgsConstructor;

import static com.example.mnist_project.util.Constants.numberOfClasses;
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

}
