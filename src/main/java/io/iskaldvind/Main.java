package io.iskaldvind;

import java.util.Arrays;

public class Main {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        fillArraySingleThread();
        fillArrayInHalves();
    }

    public static void fillArraySingleThread() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long started = System.currentTimeMillis();
        fillByFormula(arr);
        long ended = System.currentTimeMillis();
        System.out.println("Filling array with single thread took " + (ended - started) + "ms");
    }

    public static void  fillArrayInHalves() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        float[] first = new float[HALF];
        float[] second = new float[HALF];
        long started = System.currentTimeMillis();

        Thread t1 = new Thread(() -> {
            System.arraycopy(arr, 0, first, 0, HALF);
            fillByFormula(first);
            System.arraycopy(first, 0, arr, 0, HALF);
        });

        Thread t2 = new Thread(() -> {
            System.arraycopy(arr, HALF, second, 0, HALF);
            fillByFormula(second);
            System.arraycopy(second, 0, arr, HALF, HALF);
        });

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        long ended = System.currentTimeMillis();
        System.out.println("Filling array with two threads took " + (ended - started) + "ms");
    }
    
    private static void fillByFormula(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
