package com.example.mnist_project.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WatchTest {

    @Test
    public void testSimpleWatchFlow() {
        int sum = 0, n = 1000;
        Watch watch = new Watch().start();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < n; j++) {
                sum += i;
            }
        }
        assertEquals(watch.stop().get(), watch.stopAndGet());
    }

    @Test
    public void seeTheWatchResults() throws InterruptedException {
        Watch watch = new Watch().start();
        Thread.sleep(1000);
        System.out.println(watch.stopAndGet());
    }

}