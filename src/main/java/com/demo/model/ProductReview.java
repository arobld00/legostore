package com.demo.model;

import org.springframework.data.mongodb.core.index.TextIndexed;

public class ProductReview {

    @TextIndexed
    private String userName;

    public ProductReview(String userName, int rating) {
        this.userName = userName;
        this.rating = rating;
    }

    private int rating;

    public String getUserName() {
        return userName;
    }

    public int getRating() {
        return rating;
    }

}
