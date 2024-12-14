package com.study.common.dto.reservation;

import com.study.common.dto.library.LibraryResponse;
import com.study.common.en.ReservationStatus;
import com.study.common.dto.library.LibraryBookResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookReservationResponse {
    private UUID reservationUid;
    private ReservationStatus status;
    private LocalDate startDate;
    private LocalDate tillDate;
    private BookInfo book;
    private LibraryResponse library;
}

