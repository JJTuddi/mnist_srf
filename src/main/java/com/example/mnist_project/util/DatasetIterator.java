package com.example.mnist_project.util;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.Iterator;

public class DatasetIterator {

    public static Iterable<Mat> getTrainingDatasetOfClass(int clazz) {
        return () -> new DatasetIteratorImpl("train", clazz);
    }

    public static Iterable<Mat> getTestDatasetOfClass(int clazz) {
        return () -> new DatasetIteratorImpl("test", clazz);
    }

    private static class DatasetIteratorImpl implements Iterator<Mat> {

        int index = 0;
        int clazz;
        String dataset;

        public DatasetIteratorImpl(String dataset, int clazz) {
            this.clazz = clazz;
            this.dataset = dataset;
        }

        @Override
        public boolean hasNext() {
            if ("test".equals(dataset) && index > 30) {
                return false;
            }
            File file = new File(getImageName());
            if (!file.exists()) {
                return false;
            }
            return true;
        }

        @Override
        public Mat next() {
            Mat resul = Imgcodecs.imread(getImageName(), Imgcodecs.IMREAD_GRAYSCALE);
            index++;
            return resul;
        }

        private String getImageName() {
            return Constants.getImageName(dataset, clazz, index);
        }

    }

}
