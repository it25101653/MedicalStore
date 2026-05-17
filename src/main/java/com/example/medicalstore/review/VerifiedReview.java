package com.example.medicalstore.review;

public class VerifiedReview extends Review {

    public VerifiedReview(String reviewId, String userName, String userEmails,
                          String medId, int rating, String comment, String date) {
        super(reviewId, userName,userEmails, medId, rating, comment,date);
    }

    @Override
    public String getDisplayLabel() {
        return "VERIFIED";
    }
}