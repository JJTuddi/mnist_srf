package com.example.mnist_project.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.NONE;

@NoArgsConstructor(access = NONE)
public class Constants {

    public static final Integer numberOfClasses = 10;
    public static final Integer treshold = 128;
    public static final Integer imageHeight = 28;
    public static final Integer imageWidth = 28;
    public static final Integer imageSize = 28 * 28;
    public static final String imageNameTemplate = "data\\%s\\%d\\%06d.png";

    public static String getImageName(String set, Integer theClass, Integer index) {
        return String.format(imageNameTemplate, set, theClass, index);
    }

}
