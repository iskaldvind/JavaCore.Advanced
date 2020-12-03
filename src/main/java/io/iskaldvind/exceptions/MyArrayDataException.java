package io.iskaldvind.exceptions;

public class MyArrayDataException extends RuntimeException {
    String message;

    public MyArrayDataException(int subIndex, int elemIndex) {
        this.message = "Wrong data in element ["+ subIndex +"]["+ elemIndex +"]";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
