package com.example.medicalstore.review;

public class VerifiedReview extends Review {

    public VerifiedReview(String reviewId, String userName, String userEmails,
                          String medId, int rating, String comment) {
        super(reviewId, userName,userEmails, medId, rating, comment);
    }

    @Override
    public String getDisplayLabel() {
        return "VERIFIED";
    }
}