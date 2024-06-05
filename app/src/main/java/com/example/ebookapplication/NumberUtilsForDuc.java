package com.example.ebookapplication;

public class NumberUtilsForDuc {
    public static String toStringFourDigits(int numberOfPages) {
        if (numberOfPages < 0 || numberOfPages > 9999) {
            throw new IllegalArgumentException("Number must be between 0 and 9999 inclusive");
        }
        return String.format("%04d", numberOfPages);
    }
}
