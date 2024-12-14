package com.study.entity;

import com.study.common.dto.library.BookCondition;
import com.study.util.BookConditionConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_uid", nullable = false, unique = true)
    private UUID bookUid;

    @Column(nullable = false)
    private String name;

    private String author;

    private String genre;

    @Column(name = "condition")
    @Convert(converter = BookConditionConverter.class)
    private BookCondition condition = BookCondition.EXCELLENT;
}

