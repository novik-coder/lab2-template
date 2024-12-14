package com.study.controller;

import com.study.common.dto.reservation.BookReservationResponse;
import com.study.common.dto.reservation.ReturnBookRequest;
import com.study.common.dto.reservation.TakeBookRequest;
import com.study.common.dto.reservation.TakeBookResponse;
import com.study.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<BookReservationResponse>> getUserReservations(
            @RequestHeader("X-User-Name") String username
    ) {
        return ResponseEntity.ok(reservationService.getUserReservations(username));
    }

    @PostMapping
    public ResponseEntity<TakeBookResponse> takeBook(
            @RequestHeader("X-User-Name") String username,
            @Valid @RequestBody TakeBookRequest request
    ) {
        return ResponseEntity.ok(reservationService.takeBook(username, request));
    }

    @PostMapping("/{reservationUid}/return")
    public ResponseEntity<Void> returnBook(
            @PathVariable UUID reservationUid,
            @RequestHeader("X-User-Name") String username,
            @Valid @RequestBody ReturnBookRequest request
    ) {
        reservationService.returnBook(reservationUid, username, request);
        return ResponseEntity.noContent().build();
    }
}
