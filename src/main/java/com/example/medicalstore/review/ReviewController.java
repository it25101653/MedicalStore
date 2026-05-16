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
                              Model model) {
        List<Review> reviews = (medId != null && !medId.isEmpty())
                ? reviewService.getReviewsForMedicine(medId)
                : reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
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
}