package edu.ratingservice.entity;

import java.util.Date;
import java.util.UUID;

public class RatingResponse {
    private final UUID id;
    private final int rating;
    private final Date date;

    public RatingResponse(UUID id, int rating, Date date) {
        this.id = id;
        this.date = date;
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public UUID getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }
}
