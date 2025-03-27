package edu.ratingservice.entity;

public class RatingRequest {

    private int rating;

    public RatingRequest(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
