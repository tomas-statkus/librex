package com.tommkraft.librarymanagementsystem.dto;

import com.tommkraft.librarymanagementsystem.entity.BorrowLog;
import lombok.Data;
import java.util.Date;

@Data
public class BorrowLogResponse {
    private String id;
    private Long bookId;
    private Long userId;
    private Date borrowDate;
    private Date returnDate;
    private String status;

    public BorrowLogResponse(BorrowLog log) {
        this.id = log.getId();
        this.bookId = Long.valueOf(log.getBookId());
        this.userId = Long.valueOf(log.getUserId());
        this.borrowDate = log.getBorrowDate();
        this.returnDate = log.getReturnDate();
        this.status = log.getStatus();
    }
}