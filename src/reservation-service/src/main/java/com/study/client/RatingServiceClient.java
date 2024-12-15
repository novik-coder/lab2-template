package com.study.client;

import com.study.common.dto.rating.UserRatingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "rating-service")
public interface RatingServiceClient {
    @GetMapping("/api/v1/rating")
    UserRatingResponse getUserRating(@RequestHeader("X-User-Name") String username);
    @PostMapping("/api/v1/rating/increase")
    public ResponseEntity<Void> increaseRating(
            @RequestHeader("X-User-Name") String username
    );
}
