package com.tommkraft.librarymanagementsystem.controller;

import com.tommkraft.librarymanagementsystem.dto.BorrowLogRequest;
import com.tommkraft.librarymanagementsystem.dto.BorrowLogResponse;
import com.tommkraft.librarymanagementsystem.entity.BorrowLog;
import com.tommkraft.librarymanagementsystem.service.BorrowLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/borrow-logs")
@RequiredArgsConstructor
public class BorrowLogController {

    private final BorrowLogService borrowLogService;

    @PostMapping
    public ResponseEntity<BorrowLogResponse> createBorrowLog(@Valid @RequestBody BorrowLogRequest request) {
        BorrowLog log = new BorrowLog();
        log.setBookId(String.valueOf(request.getBookId()));
        log.setUserId(String.valueOf(request.getUserId()));
        log.setStatus("BORROWED");
        BorrowLog createdLog = borrowLogService.createBorrowLog(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BorrowLogResponse(createdLog));
    }

    @PutMapping("/{logId}/return")
    public ResponseEntity<?> markAsReturned(@PathVariable Long logId) {
        try {
            BorrowLog updatedLog = borrowLogService.updateReturnDate(Long.valueOf(String.valueOf(logId)), "RETURNED");
            return ResponseEntity.ok(new BorrowLogResponse(updatedLog));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Borrow log not found"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowLogResponse>> getLogsByUser(@PathVariable Long userId) {
        List<BorrowLogResponse> responses = borrowLogService.getLogsByUser(userId)
                .stream().map(BorrowLogResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BorrowLogResponse>> getLogsByBook(@PathVariable Long bookId) {
        List<BorrowLogResponse> responses = borrowLogService.getLogsByBook(bookId)
                .stream().map(BorrowLogResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public ResponseEntity<?> getAllBorrowLogs() {
        try {
            List<BorrowLogResponse> responses = borrowLogService.getAllBorrowLogs()
                    .stream().map(BorrowLogResponse::new).collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Server error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/api/admin/borrow-logs/delete/{id}")
    public ResponseEntity<?> deleteBorrowLog(@PathVariable String id) {
        try {
            borrowLogService.deleteBorrowLog(id);
            return ResponseEntity.ok(Map.of("message", "Log deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Borrow log not found or invalid ID"));
        }
    }



}
