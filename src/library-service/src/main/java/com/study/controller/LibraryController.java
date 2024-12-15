package com.study.controller;

import com.study.common.dto.library.*;
import com.study.service.LibraryService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/libraries")
@RequiredArgsConstructor
@Validated
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping
    public ResponseEntity<LibraryPaginationResponse> getLibraries(
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size,
            @RequestParam @NotBlank String city
    ) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<LibraryResponse> librariesPage = libraryService.getLibrariesByCity(city, pageable);

        LibraryPaginationResponse response = new LibraryPaginationResponse(
                page,
                size,
                librariesPage.getTotalElements(),
                librariesPage.getContent()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{libraryUid}/books")
    public ResponseEntity<LibraryBookPaginationResponse> getLibraryBooks(
            @PathVariable UUID libraryUid,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size,
            @RequestParam(defaultValue = "false") Boolean showAll
    ) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<LibraryBookResponse> booksPage = libraryService.getLibraryBooks(libraryUid, showAll, pageable);

        LibraryBookPaginationResponse response = new LibraryBookPaginationResponse(
                page,
                size,
                booksPage.getTotalElements(),
                booksPage.getContent()
        );

        return ResponseEntity.ok(response);
    }


    // 获取图书馆信息
    @GetMapping("/{libraryUid}")
    public LibraryResponse getLibraryInfo(@PathVariable UUID libraryUid) {
        return libraryService.getLibraryInfo(libraryUid);
    }

    // 检查书籍的可用性
    @GetMapping("/checkBookAvailability/{libraryUid}/books")
    public LibraryBookResponse checkBookAvailability(
            @PathVariable UUID libraryUid,
            @RequestParam UUID bookUid
    ) {
        return libraryService.checkBookAvailability(libraryUid, bookUid);
    }


    @PutMapping("/borrow/{libraryUid}/books/{bookUid}")
    void borrowBookAvailability(
            @PathVariable UUID libraryUid,
            @PathVariable UUID bookUid
    ){

        libraryService.borrowBookAvailability(libraryUid, bookUid);
    }
    @PutMapping("/return/{libraryUid}/books/{bookUid}")
    void returnBookAvailability(
            @PathVariable UUID libraryUid,
            @PathVariable UUID bookUid
    ){
        libraryService.returnBookAvailability(libraryUid, bookUid);

    }


}
