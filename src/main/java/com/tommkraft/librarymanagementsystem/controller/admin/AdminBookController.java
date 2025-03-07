package com.tommkraft.librarymanagementsystem.controller.admin;

import com.tommkraft.librarymanagementsystem.entity.Book;
import com.tommkraft.librarymanagementsystem.repository.jpa.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    private final BookRepository bookRepository;

    public AdminBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}