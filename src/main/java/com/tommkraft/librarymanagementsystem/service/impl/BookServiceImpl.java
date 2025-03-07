package com.tommkraft.librarymanagementsystem.service.impl;

import com.tommkraft.librarymanagementsystem.entity.Book;
import com.tommkraft.librarymanagementsystem.repository.jpa.BookRepository;
import com.tommkraft.librarymanagementsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}