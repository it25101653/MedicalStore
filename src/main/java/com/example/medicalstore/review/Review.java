package com.example.medicalstore.review;

//Abstract class
public abstract class Review {

    //Attributes for review
    private final String reviewId;
    private final String userName;
    private final String userEmails;
    private final String medId;
    private final int rating;
    private final String comment;

    //paramerized constructor
    public Review(String reviewId, String userName,String userEmails, String medId, int rating, String comment) {
        this.reviewId = reviewId;
        this.userName   = userName;
        this.userEmails = userEmails;
        this.medId    = medId;
        this.rating   = rating;
        this.comment  = comment;
    }

    //getters
    public String getReviewId() {
        return reviewId;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserEmails(){
        return userEmails;
    }
    public String getMedId(){
        return medId;
    }
    public int getRating() {
        return rating;
    }
    public String getComment() {
        return comment;
    }


    public abstract String getDisplayLabel();

    public String toFileString() {
        return reviewId+","+userName+","+userEmails+","+medId+","+rating+","+comment+","+getDisplayLabel();
    }

    public static Review fromFileString(String line) {
        String[] p = line.split(",", 7);
        return new VerifiedReview(p[0], p[1], p[2], p[3], Integer.parseInt(p[4]), p[5]);
    }
}