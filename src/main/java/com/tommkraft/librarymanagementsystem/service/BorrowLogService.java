package com.tommkraft.librarymanagementsystem.service;

import com.tommkraft.librarymanagementsystem.entity.BorrowLog;
import java.util.List;

public interface BorrowLogService {
    BorrowLog createBorrowLog(BorrowLog borrowLog);
    BorrowLog updateReturnDate(Long logId, String status);
    List<BorrowLog> getLogsByUser(Long userId);
    List<BorrowLog> getLogsByBook(Long bookId);
    List<BorrowLog> getAllBorrowLogs();
    void deleteBorrowLog(String id);
}