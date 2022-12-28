package com.example.mnist_project.util;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class DatasetIterator implements Serializable {

    private static final String TEST = "test";
    private static final String TRAIN = "train";

    public abstract Iterable<Mat> getTrainingDataOfClass(int clazz);
    public abstract Iterable<Mat> getTestDataOfClass(int clazz);

    public static DatasetIterator of() {
        return new DatasetIterator() {
            @Override
            public Iterable<Mat> getTrainingDataOfClass(int clazz) {
                return () -> new DatasetIteratorImpl(TRAIN, clazz);
            }

            @Override
            public Iterable<Mat> getTestDataOfClass(int clazz) {
                return () -> new DatasetIteratorImpl(TEST, clazz);
            }
        };
    }

    public static DatasetIterator of(int startingIndex, int maxIndex) {
        return new DatasetIterator() {
            @Override
            public Iterable<Mat> getTrainingDataOfClass(final int clazz) {
                return () -> new DatasetIteratorImpl(TRAIN, clazz, startingIndex, maxIndex);
            }

            @Override
            public Iterable<Mat> getTestDataOfClass(final int clazz) {
                return () -> new DatasetIteratorImpl(TEST, clazz, startingIndex, maxIndex);
            }
        };
    }

    public static DatasetIterator all() {
        return new DatasetIterator() {
            @Override
            public Iterable<Mat> getTrainingDataOfClass(final int clazz) {
                return getAll(TRAIN, clazz);
            }

            @Override
            public Iterable<Mat> getTestDataOfClass(final int clazz) {
                return getAll(TEST, clazz);
            }

            private Iterable<Mat> getAll(String set, final int clazz) {
                List<Mat> result = new LinkedList<>();
                int index = 0;
                while (true) {
                    String imageName = Constants.getImageName(set, clazz, index++);
                    File file = new File(imageName);
                    if (!file.exists()) {
                        break;
                    }
                    result.add(Imgcodecs.imread(imageName, Imgcodecs.IMREAD_GRAYSCALE));
                }
                return result;
            }

        };
    }

    private static class DatasetIteratorImpl implements Iterator<Mat>, Serializable {

        int index = 0;
        int clazz;
        int maxIndex = -1;
        String dataset;

        public DatasetIteratorImpl(String dataset, int clazz) {
            this.clazz = clazz;
            this.dataset = dataset;
        }

        public DatasetIteratorImpl(String dataset, int clazz, int startingIndex, int maxIndex) {
            this.clazz = clazz;
            this.dataset = dataset;
            this.index = startingIndex;
            this.maxIndex = maxIndex;
        }

        @Override
        public boolean hasNext() {
            if (maxIndex != -1 && index >= maxIndex) {
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