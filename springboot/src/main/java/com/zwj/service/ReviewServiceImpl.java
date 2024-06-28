package com.zwj.service;

import com.zwj.mapper.ReviewMapper;
import com.zwj.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public int addReview(Review review) {
        return reviewMapper.insert(review);
    }

    @Override
    public int deleteReview(int id) {
        return reviewMapper.deleteById(id);
    }

    @Override
    public int updateReview(Review review) {
        return reviewMapper.update(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewMapper.selectAll();
    }
}
