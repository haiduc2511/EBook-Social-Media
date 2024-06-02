package com.example.ebookapplication;

import java.util.List;

public class MathUtilsForDuc {
    public static double getRatingAverage(List<BookRatingModel> bookRatingList) {
        double ratingSum = 0;
        if (bookRatingList.isEmpty()) {
            return 0;
        }
        for (BookRatingModel bookRatingModel : bookRatingList) {
            ratingSum += Double.parseDouble(bookRatingModel.star);
        }
        return ratingSum / bookRatingList.size();
    }
}
