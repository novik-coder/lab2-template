package com.study.common.dto.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryPaginationResponse {
    private Integer page;
    private Integer pageSize;
    private Long totalElements;
    private List<LibraryResponse> items;
}
