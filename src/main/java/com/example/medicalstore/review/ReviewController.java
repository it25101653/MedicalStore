package com.example.medicalstore.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//review controller class
@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    //method for show reviews
    @GetMapping("/reviews")
    public String showReviews(@RequestParam(required = false) String medId,
                              @RequestParam(required = false) Integer rating,
                              Model model) {
        List<Review> reviews;
        if (rating != null) {
            reviews = reviewService.getReviewsByRating(rating);
        } else if (medId != null && !medId.isEmpty()) {
            reviews = reviewService.getReviewsForMedicine(medId);
        } else {
            reviews = reviewService.getAllReviews();
        }
        model.addAttribute("reviews", reviews);
        model.addAttribute("medId", medId);
        model.addAttribute("rating", rating);
        return "reviews";
    }
    //add review method
    @PostMapping("/reviews/add")
    public String addReview(@RequestParam String userName,
                            @RequestParam String userEmails,
                            @RequestParam String medId,
                            @RequestParam int rating,
                            @RequestParam String comment) {
        reviewService.addReview(userName, userEmails, medId, rating, comment);
        return "redirect:/reviews?medId=" + medId;
    }

    //edit method deleted

    @GetMapping("/reviews/delete")
    public String deleteReview(@RequestParam String id) {
        reviewService.delete(id);
        return "redirect:/reviews";
    }

    //like a review
    @GetMapping("/reviews/like")
    public String likeReview(@RequestParam String id) {
        reviewService.likeReview(id);
        return "redirect:/reviews";
    }

    //dislike a review
    @GetMapping("/reviews/dislike")
    public String dislikeReview(@RequestParam String id) {
        reviewService.dislikeReview(id);
        return "redirect:/reviews";
    }
}