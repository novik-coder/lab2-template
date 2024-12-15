package com.study.common.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.common.dto.library.BookCondition;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookRequest {
    @NotNull
    private BookCondition condition;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
