package com.tommkraft.librarymanagementsystem.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

// BorrowLog Entity (MongoDB)

@Document(collection = "borrow_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BorrowLog {
    @Id
    private String id;
    private String bookId;
    private String userId;
    private Date borrowDate;
    private Date returnDate;
    private String status;
}