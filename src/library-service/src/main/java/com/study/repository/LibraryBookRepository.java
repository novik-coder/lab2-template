package com.study.repository;

import com.study.entity.LibraryBook;
import com.study.entity.LibraryBookId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LibraryBookRepository extends JpaRepository<LibraryBook, LibraryBookId> {
    // 如果需要获取所有图书（不分页）
    List<LibraryBook> findByIdLibraryId(Long libraryId);



    // 如果需要按照可用数量过滤

    @Query(
            value = """
        SELECT 
            b.book_uid as bookUid,
            b.name,
            b.author,
            b.genre,
            b.condition,
            lb.available_count as availableCount
        FROM library_books lb
        JOIN books b ON b.id = lb.book_id
        JOIN library l ON l.id = lb.library_id
        WHERE l.library_uid = :libraryUid
        AND (:showAll = true OR lb.available_count > 0)
    """,
            countQuery = """
        SELECT count(*)
        FROM library_books lb
        JOIN library l ON l.id = lb.library_id
        WHERE l.library_uid = :libraryUid
        AND (:showAll = true OR lb.available_count > 0)
    """,
            nativeQuery = true
    )
    Page<BookProjection> findLibraryBooksWithDetails(
            @Param("libraryUid") UUID libraryUid,
            @Param("showAll") boolean showAll,
            Pageable pageable
    );






    @Query(
            value = """
        SELECT 
            b.book_uid as bookUid,
            b.name,
            b.author,
            b.genre,
            b.condition,
            lb.available_count as availableCount
        FROM library_books lb
        JOIN books b ON b.id = lb.book_id
        JOIN library l ON l.id = lb.library_id
        WHERE l.library_uid = :libraryUid
        AND b.book_uid = :bookUid
        AND lb.available_count > 0
        """,

            nativeQuery = true
    )
    Optional<BookProjection> findLibraryBookResponseByLibraryUidAndBookUid(
            @Param("libraryUid") UUID libraryUid,
            @Param("bookUid") UUID bookUid
    );

    @Query(
            value = """
        SELECT 
           lb.*
        FROM library_books lb
        JOIN books b ON b.id = lb.book_id
        JOIN library l ON l.id = lb.library_id
        WHERE l.library_uid = :libraryUid
        AND b.book_uid = :bookUid
        AND lb.available_count > 0
        """,

            nativeQuery = true
    )
    LibraryBook findByLibraryUidAndBookUid(UUID libraryUid, UUID bookUid);
}

