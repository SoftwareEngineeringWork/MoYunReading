package com.zwj.service;

import com.zwj.pojo.Review;

import java.util.List;

public interface ReviewService {
    int addReview(Review review);
    int deleteReview(int id);
    int updateReview(Review review);
    List<Review> getAllReviews();
}
