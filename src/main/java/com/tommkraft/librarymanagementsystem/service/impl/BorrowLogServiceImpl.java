package com.tommkraft.librarymanagementsystem.service.impl;

import com.tommkraft.librarymanagementsystem.entity.BorrowLog;
import com.tommkraft.librarymanagementsystem.repository.mongodb.BorrowLogRepository;
import com.tommkraft.librarymanagementsystem.service.BorrowLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowLogServiceImpl implements BorrowLogService {
    private final BorrowLogRepository borrowLogRepository;

    @Override
    public BorrowLog createBorrowLog(BorrowLog borrowLog) {
        if (borrowLog.getBorrowDate() == null) { // ✅ Only set if missing
            borrowLog.setBorrowDate(new Date());
        }
        if (borrowLog.getStatus() == null || borrowLog.getStatus().isEmpty()) { // ✅ Only set if missing
            borrowLog.setStatus("BORROWED");
        }
        return borrowLogRepository.save(borrowLog);
    }

    @Override
    public void deleteBorrowLog(String id) {
        if (borrowLogRepository.existsById(id)) {
            borrowLogRepository.deleteById(id);
        } else {
            throw new RuntimeException("Borrow log not found");
        }
    }

    @Override
    public BorrowLog updateReturnDate(Long logId, String status) {
        BorrowLog log = borrowLogRepository.findById(String.valueOf(logId))
                .orElseThrow(() -> new RuntimeException("Borrow log not found"));

        log.setReturnDate(new Date());
        log.setStatus(status);
        return borrowLogRepository.save(log);
    }

    @Override
    public List<BorrowLog> getLogsByUser(Long userId) {
        return borrowLogRepository.findByUserId(userId);
    }

    @Override
    public List<BorrowLog> getLogsByBook(Long bookId) {
        return borrowLogRepository.findByBookId(bookId);
    }

    @Override
    public List<BorrowLog> getAllBorrowLogs() {
        return borrowLogRepository.findAll();
    }
}