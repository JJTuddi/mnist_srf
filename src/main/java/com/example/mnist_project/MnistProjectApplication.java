package com.example.mnist_project;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.slf4j.SLF4JLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.apache.tomcat.jni.Library.loadLibrary;

@SpringBootApplication
public class MnistProjectApplication {

    static {
        loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        SpringApplication.run(MnistProjectApplication.class, args);
    }

}
