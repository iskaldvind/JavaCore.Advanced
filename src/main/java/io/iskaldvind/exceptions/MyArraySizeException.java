package io.iskaldvind.exceptions;

public class MyArraySizeException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Array has wrong size";
    }
}
