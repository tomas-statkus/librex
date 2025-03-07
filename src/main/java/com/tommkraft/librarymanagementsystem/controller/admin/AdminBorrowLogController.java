package com.tommkraft.librarymanagementsystem.controller.admin;

import com.tommkraft.librarymanagementsystem.entity.BorrowLog;
import com.tommkraft.librarymanagementsystem.service.BorrowLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/borrow-logs")
@RequiredArgsConstructor
public class AdminBorrowLogController {

    private final BorrowLogService borrowLogService;

    @PostMapping("/add")
    public ResponseEntity<BorrowLog> addLog(@RequestBody BorrowLog log) {
        BorrowLog savedLog = borrowLogService.createBorrowLog(log);
        return ResponseEntity.ok(savedLog);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLog(@PathVariable String id) {
        try {
            borrowLogService.deleteBorrowLog(id);
            return ResponseEntity.ok(Map.of("message", "Log deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Borrow log not found or invalid ID"));
        }
    }
}