package com.study.common.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TakeBookRequest {
    @NotNull
    private UUID bookUid;

    @NotNull
    private UUID libraryUid;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate tillDate;
}
