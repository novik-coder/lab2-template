package com.study.util;

import com.study.common.dto.library.BookCondition;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookConditionConverter implements AttributeConverter<BookCondition, String> {

    @Override
    public String convertToDatabaseColumn(BookCondition condition) {
        if (condition == null) {
            return null;
        }
        return condition.name();
    }

    @Override
    public BookCondition convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return BookCondition.valueOf(dbData);
    }
}
