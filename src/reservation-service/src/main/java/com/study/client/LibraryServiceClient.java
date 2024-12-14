package com.study.client;

import com.study.common.dto.library.BookCondition;
import com.study.common.dto.library.LibraryBookResponse;
import com.study.common.dto.library.LibraryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "library-service")
public interface LibraryServiceClient {
    @GetMapping("/api/v1/libraries/checkBookAvailability/{libraryUid}/books")
    LibraryBookResponse checkBookAvailability(
            @PathVariable UUID libraryUid,
            @RequestParam UUID bookUid
    );

    @GetMapping("/api/v1/libraries/{libraryUid}")
    LibraryResponse getLibraryInfo(@PathVariable UUID libraryUid);


    @PutMapping("/api/v1/libraries/borrow/{libraryUid}/books/{bookUid}")
    void borrowBookAvailability(
            @PathVariable UUID libraryUid,
            @PathVariable UUID bookUid
    );
    @PutMapping("/api/v1/libraries/return/{libraryUid}/books/{bookUid}")
    void returnBookAvailability(
            @PathVariable UUID libraryUid,
            @PathVariable UUID bookUid
    );

}

