package com.study.service;

import com.study.client.LibraryServiceClient;
import com.study.client.RatingServiceClient;
import com.study.common.dto.library.LibraryBookResponse;
import com.study.common.dto.rating.UserRatingResponse;
import com.study.common.dto.reservation.*;
import com.study.common.en.ReservationStatus;
import com.study.common.exception.BookNotAvailableException;
import com.study.common.exception.InvalidOperationException;
import com.study.common.exception.ResourceNotFoundException;
import com.study.entity.Reservation;
import com.study.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final LibraryServiceClient libraryServiceClient;
    private final RatingServiceClient ratingServiceClient;

    public List<BookReservationResponse> getUserReservations(String username) {
        List<Reservation> reservations = reservationRepository.findByUsername(username);
        return reservations.stream()
                .map(this::toBookReservationResponse)
                .collect(Collectors.toList());
    }

    public TakeBookResponse takeBook(String username, TakeBookRequest request) {
        // 检查图书是否可用
        LibraryBookResponse bookInfo = libraryServiceClient.checkBookAvailability(
                request.getLibraryUid(),
                request.getBookUid()
        );

        if (bookInfo.getAvailableCount() <= 0) {
            throw new BookNotAvailableException("Book is not available for reservation");
        }


        // 更新图书馆中的可用书籍数量
        libraryServiceClient.borrowBookAvailability(request.getLibraryUid(), request.getBookUid());

        // 创建预订
        Reservation reservation = new Reservation();
        reservation.setUsername(username);
        reservation.setBookUid(request.getBookUid());
        reservation.setLibraryUid(request.getLibraryUid());
        reservation.setStatus(ReservationStatus.RENTED);
        reservation.setStartDate(LocalDateTime.now());
        reservation.setTillDate(request.getTillDate().atTime(23, 59, 59));

        reservation = reservationRepository.save(reservation);

        // 获取用户评分
        UserRatingResponse rating = ratingServiceClient.getUserRating(username);

        return toTakeBookResponse(reservation, bookInfo, rating);
    }

    public void returnBook(UUID reservationUid, String username, ReturnBookRequest request) {
        Reservation reservation = reservationRepository.findByReservationUidAndUsername(reservationUid, username)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.RENTED) {
            throw new InvalidOperationException("Book is already returned or expired");
        }

        // 更新预订状态
        reservation.setStatus(ReservationStatus.RETURNED);
        reservationRepository.save(reservation);

        // 通知图书馆服务更新图书状态
        libraryServiceClient.returnBookAvailability(
                reservation.getLibraryUid(),
                reservation.getBookUid()
        );
        ratingServiceClient.increaseRating(username);
    }

    private BookReservationResponse toBookReservationResponse(Reservation reservation) {
        LibraryBookResponse bookInfo = libraryServiceClient.checkBookAvailability(
                reservation.getLibraryUid(),
                reservation.getBookUid()
        );

        return BookReservationResponse.builder()
                .reservationUid(reservation.getReservationUid())
                .status(reservation.getStatus())
                .startDate(reservation.getStartDate().toLocalDate())
                .tillDate(reservation.getTillDate().toLocalDate())
                .book(new BookInfo(
                        bookInfo.getBookUid(),
                        bookInfo.getName(),
                        bookInfo.getAuthor(),
                        bookInfo.getGenre()
                ))
                .library(libraryServiceClient.getLibraryInfo(reservation.getLibraryUid()))
                .build();
    }

    private TakeBookResponse toTakeBookResponse(
            Reservation reservation,
            LibraryBookResponse bookInfo,
            UserRatingResponse rating
    ) {
        return TakeBookResponse.builder()
                .reservationUid(reservation.getReservationUid())
                .status(reservation.getStatus())
                .startDate(reservation.getStartDate().toLocalDate())
                .tillDate(reservation.getTillDate().toLocalDate())
                .book(new BookInfo(
                        bookInfo.getBookUid(),
                        bookInfo.getName(),
                        bookInfo.getAuthor(),
                        bookInfo.getGenre()
                ))
                .library(libraryServiceClient.getLibraryInfo(reservation.getLibraryUid()))
                .rating(rating)
                .build();
    }
}
