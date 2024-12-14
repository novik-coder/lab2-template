package com.study.repository;

import com.study.common.dto.library.BookCondition;

import java.util.UUID;
public interface BookProjection {
    UUID getBookUid();
    String getName();
    String getAuthor();
    String getGenre();
    BookCondition getCondition();
    Integer getAvailableCount();
}
