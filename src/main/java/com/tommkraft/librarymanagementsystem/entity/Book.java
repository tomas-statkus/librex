package com.tommkraft.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

// Book Entity (PostgreSQL)

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Correct JPA annotation
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;
    private Boolean isAvailable = true; // Default value handled in-app
}