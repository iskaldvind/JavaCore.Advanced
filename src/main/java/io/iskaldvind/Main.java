package io.iskaldvind;

import io.iskaldvind.exceptions.MyArrayDataException;
import io.iskaldvind.exceptions.MyArraySizeException;

import java.util.Arrays;

public class Main {
    static final int VALID_SIZE = 4;
    private static final String[][] WRONG_SIZE_ARRAY = {{"1", "2"}, {"3", "4"}, {"5", "6"}};
    private static final String[][] WRONG_DATA_ARRAY = {
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"},
            {"1", "one", "1", "1"},
            {"1", "1", "1", "1"}
    };
    private static final String[][] VALID_ARRAY = {
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"},
            {"1", "1", "1", "1"}
    };
    private static final String[][][] ARRAY_OF_ARRAYS = {WRONG_SIZE_ARRAY, WRONG_DATA_ARRAY, VALID_ARRAY};

    public static void main(String[] args) {
        for (int arrayIndex = 0; arrayIndex < ARRAY_OF_ARRAYS.length; arrayIndex++) {
            int arrayNumber = arrayIndex + 1;
            try {
                System.out.println("Sum of array №" + arrayNumber + " is: " + sumArrayElements(ARRAY_OF_ARRAYS[arrayIndex]));
            } catch (MyArraySizeException | MyArrayDataException e) {
                System.out.println("Caught " + e.getClass().getSimpleName() + " in array №" + arrayNumber + ": " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public static int sumArrayElements(String[][] array) throws RuntimeException {
        if (array.length != VALID_SIZE) throw new MyArraySizeException();
        for (String[] subarray: array) {
            if (subarray.length != VALID_SIZE) throw new MyArraySizeException();
        }

        int sum = 0;
        for (int subIndex = 0; subIndex < VALID_SIZE; subIndex++) {
            for (int elemIndex = 0; elemIndex < VALID_SIZE; elemIndex++) {
                try {
                    int elem = Integer.parseInt(array[subIndex][elemIndex]);

                    sum += elem;
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(subIndex, elemIndex);
                }
            }
        }
        return sum;
    }
}
