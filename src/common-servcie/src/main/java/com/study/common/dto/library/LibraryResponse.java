package com.study.common.dto.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryResponse {
    private UUID libraryUid;
    private String name;
    private String address;
    private String city;
}
