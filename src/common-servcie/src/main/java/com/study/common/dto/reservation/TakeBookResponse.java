package com.study.common.dto.reservation;

import com.study.common.dto.rating.UserRatingResponse;
import com.study.common.en.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.study.common.dto.library.LibraryResponse;


import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeBookResponse {
    private UUID reservationUid;
    private ReservationStatus status;
    private LocalDate startDate;
    private LocalDate tillDate;
    private BookInfo book;
    private LibraryResponse library;
    private UserRatingResponse rating;
}
