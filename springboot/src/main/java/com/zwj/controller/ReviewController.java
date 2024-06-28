package com.zwj.controller;

import com.zwj.pojo.Review;
import com.zwj.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    @PostMapping("/add")
    public int addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @DeleteMapping("/delete/{id}")
    public int deleteReview(@PathVariable int id) {
        return reviewService.deleteReview(id);
    }

    @PutMapping("/update")
    public int updateReview(@RequestBody Review review) {
        return reviewService.updateReview(review);
    }

    @GetMapping("/all")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }
}
