package com.study.entity;

import com.study.common.en.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_uid", nullable = false, unique = true)
    private UUID reservationUid;

    @Column(nullable = false, length = 80)
    private String username;

    @Column(name = "book_uid", nullable = false)
    private UUID bookUid;

    @Column(name = "library_uid", nullable = false)
    private UUID libraryUid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "till_date", nullable = false)
    private LocalDateTime tillDate;

    @PrePersist
    public void prePersist() {
        if (reservationUid == null) {
            reservationUid = UUID.randomUUID();
        }
    }
}
