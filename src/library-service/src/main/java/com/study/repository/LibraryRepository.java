package com.study.repository;

import com.study.entity.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    Optional<Library> findByLibraryUid(UUID libraryUid);
    Page<Library> findByCity(String city, Pageable pageable);

}
