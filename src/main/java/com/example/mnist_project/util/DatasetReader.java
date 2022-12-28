package com.example.mnist_project.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.opencv.core.Mat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.mnist_project.util.Constants.numberOfClasses;

public class DatasetReader implements Serializable {

    public static List<List<Mat>> getTrainingImages() {
        return IntStream.range(0, numberOfClasses).boxed().parallel()
                .map(currentClass -> {
                    List<Mat> result = new ArrayList<>();
                    for (Mat mat: DatasetIterator.of().getTrainingDataOfClass(currentClass)) {
                        result.add(mat);
                    }
                    return ImagesPair.builder()
                            .images(result)
                            .currentClass(currentClass)
                            .build();
                })
                .sorted(Comparator.comparing(ImagesPair::getCurrentClass))
                .map(ImagesPair::getImages)
                .collect(Collectors.toList());
    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static final class ImagesPair {

        private final int currentClass;
        private final List<Mat> images;

    }

}
