package com.study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBookId implements Serializable {
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "library_id")
    private Long libraryId;
}
