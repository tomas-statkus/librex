package com.tommkraft.librarymanagementsystem.repository.mongodb;

import com.tommkraft.librarymanagementsystem.entity.BorrowLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BorrowLogRepository extends MongoRepository<BorrowLog, String> {
    List<BorrowLog> findByUserId(Long userId);
    List<BorrowLog> findByBookId(Long bookId);
}

