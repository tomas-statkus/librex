package com.tommkraft.librarymanagementsystem.service;

import com.tommkraft.librarymanagementsystem.entity.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book createBook(Book book);
    Book getBookById(Long id);
}