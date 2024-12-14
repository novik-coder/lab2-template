package com.study.service;

import com.study.common.dto.rating.UserRatingResponse;
import com.study.entity.Rating;
import com.study.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private static final int DEFAULT_RATING = 80;
    private static final int PENALTY_POINTS = 10;
    private static final int MINIMUM_RATING = 1;

    public UserRatingResponse getUserRating(String username) {
        Rating rating = ratingRepository.findByUsername(username)
                .orElseGet(() -> createDefaultRating(username));

        return new UserRatingResponse(rating.getStars());
    }

    public void decreaseRating(String username) {
        Rating rating = ratingRepository.findByUsername(username)
                .orElseGet(() -> createDefaultRating(username));

        int newStars = Math.max(MINIMUM_RATING, rating.getStars() - PENALTY_POINTS);
        rating.setStars(newStars + 1);

        ratingRepository.save(rating);
    }

    private Rating createDefaultRating(String username) {
        Rating rating = new Rating();
        rating.setUsername(username);
        rating.setStars(DEFAULT_RATING);
        return ratingRepository.save(rating);
    }

    public void increaseRating(String username) {
        Rating rating = ratingRepository.findByUsername(username)
                .orElseGet(() -> createDefaultRating(username));

        rating.setStars(rating.getStars() + 1);

        ratingRepository.save(rating);


    }
}
