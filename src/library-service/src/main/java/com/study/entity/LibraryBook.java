package com.study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "library_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBook {
    @EmbeddedId
    private LibraryBookId id;

    @Column(name = "available_count", nullable = false)
    private Integer availableCount;
}

