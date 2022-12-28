package com.example.mnist_project.util;

public class Watch {

    private static enum WatchState {
        IDLE, RUNNING, DONE;
    }

    private WatchState state = WatchState.IDLE;
    private long start = 0;
    private long end = 0;

    public Watch() {
    }

    public Watch start() {
        if (state == WatchState.IDLE) {
            state = WatchState.RUNNING;
            start = System.nanoTime();
        } else {
            throw new IllegalStateException("You must reset the watch before starting it.");
        }
        return this;
    }

    public Watch stop() {
        if (state == WatchState.RUNNING) {
            state = WatchState.DONE;
            end = System.nanoTime();
        } else {
            throw new IllegalStateException("Your watch is not running, can't be stop.");
        }
        return this;
    }

    public double get() {
        if (state == WatchState.DONE) {
            return (end - start) / 1e6;
        } else {
            throw new IllegalStateException("Your watch is not in the done state, can't get the measured time.");
        }
    }

    public double stopAndGet() {
        if (state == WatchState.RUNNING) {
            stop();
        }
        if (state == WatchState.DONE) {
            return get();
        }
        throw new IllegalStateException("Can't get the result from this state.");
    };

}
