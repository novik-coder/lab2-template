package com.study.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "library")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "library_uid", nullable = false, unique = true)
    private UUID libraryUid;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;
}
