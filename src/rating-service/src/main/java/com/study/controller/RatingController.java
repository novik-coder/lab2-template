package com.study.controller;

import com.study.common.dto.rating.UserRatingResponse;
import com.study.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<UserRatingResponse> getUserRating(
            @RequestHeader("X-User-Name") String username
    ) {
        return ResponseEntity.ok(ratingService.getUserRating(username));
    }

    // 内部API，供其他服务调用
    @PostMapping("/decrease")
    public ResponseEntity<Void> decreaseRating(
            @RequestHeader("X-User-Name") String username
    ) {
        ratingService.decreaseRating(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/increase")
    public ResponseEntity<Void> increaseRating(
            @RequestHeader("X-User-Name") String username
    ) {
        ratingService.increaseRating(username);
        return ResponseEntity.ok().build();
    }
}
