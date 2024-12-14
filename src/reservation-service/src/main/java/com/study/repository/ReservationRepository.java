package com.study.repository;

import com.study.common.en.ReservationStatus;
import com.study.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByReservationUid(UUID reservationUid);

    List<Reservation> findByUsername(String username);

    Optional<Reservation> findByReservationUidAndUsername(UUID reservationUid, String username);

    @Query("SELECT r FROM Reservation r WHERE r.username = :username AND r.status = :status")
    List<Reservation> findByUsernameAndStatus(
            @Param("username") String username,
            @Param("status") ReservationStatus status
    );
}
