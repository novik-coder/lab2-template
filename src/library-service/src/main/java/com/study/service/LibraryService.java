package com.study.service;

import com.study.common.dto.library.BookCondition;
import com.study.common.dto.library.LibraryBookResponse;
import com.study.common.dto.library.LibraryResponse;
import com.study.entity.Book;
import com.study.entity.Library;
import com.study.entity.LibraryBook;
import com.study.repository.BookProjection;
import com.study.repository.BookRepository;
import com.study.repository.LibraryBookRepository;
import com.study.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final LibraryBookRepository libraryBookRepository;

    public Page<LibraryResponse> getLibrariesByCity(String city, Pageable pageable) {
        Page<Library> libraries = libraryRepository.findByCity(city, pageable);
        return libraries.map(this::toLibraryResponse);
    }


    private LibraryResponse toLibraryResponse(Library library) {
        return new LibraryResponse(
                library.getLibraryUid(),
                library.getName(),
                library.getAddress(),
                library.getCity()
        );
    }

    private LibraryBookResponse toLibraryBookResponse(Book book, Integer availableCount) {
        return new LibraryBookResponse(
                book.getBookUid(),
                book.getName(),
                book.getAuthor(),
                book.getGenre(),
                book.getCondition(),
                availableCount
        );
    }

    public Page<LibraryBookResponse> getLibraryBooks(UUID libraryUid, Boolean showAll, Pageable pageable) {

        return libraryBookRepository.findLibraryBooksWithDetails(libraryUid, showAll, pageable)
                .map(result -> new LibraryBookResponse(
                        result.getBookUid(),
                        result.getName(),
                        result.getAuthor(),
                        result.getGenre(),
                        result.getCondition(),
                        result.getAvailableCount()
                ));
    }

    /**
     * 获取图书馆信息
     *
     * @param libraryUid 图书馆 UID
     * @return 图书馆信息
     */
    public LibraryResponse getLibraryInfo(UUID libraryUid) {
        // 假设我们从数据库中查询图书馆信息
        Library library = libraryRepository.findByLibraryUid(libraryUid)
                .orElseThrow(() -> new IllegalArgumentException("Library not found"));

        // 将数据库实体转换为 DTO
        return new LibraryResponse(
                library.getLibraryUid(),
                library.getName(),
                library.getAddress(),
                library.getCity()
        );
    }

    /**
     * 检查图书馆中书籍的可用性
     *
     * @param libraryUid 图书馆 UID
     * @param bookUid    书籍 UID
     * @return 书籍可用性信息
     */
    public LibraryBookResponse checkBookAvailability(UUID libraryUid, UUID bookUid) {
        // 查询图书馆和书籍的可用性
        BookProjection bookProjection = libraryBookRepository.findLibraryBookResponseByLibraryUidAndBookUid(libraryUid, bookUid)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // 转换为 LibraryBookResponse
        return new LibraryBookResponse(
                bookProjection.getBookUid(),
                bookProjection.getName(),
                bookProjection.getAuthor(),
                bookProjection.getGenre(),
                bookProjection.getCondition(),
                bookProjection.getAvailableCount()
        );


    }


    // 更新图书可用数量
    public void returnBookAvailability(UUID libraryUid, UUID bookUid) {
        LibraryBook libraryBook = libraryBookRepository.findByLibraryUidAndBookUid(libraryUid, bookUid);


        libraryBook.setAvailableCount(libraryBook.getAvailableCount() + 1);
        libraryBookRepository.save(libraryBook);
    }

    public void borrowBookAvailability(UUID libraryUid, UUID bookUid) {
        LibraryBook libraryBook = libraryBookRepository.findByLibraryUidAndBookUid(libraryUid, bookUid);


        libraryBook.setAvailableCount(libraryBook.getAvailableCount() - 1);
        libraryBookRepository.save(libraryBook);
    }

}
