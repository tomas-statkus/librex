package com.tommkraft.librarymanagementsystem.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class BorrowLogRequest {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "User ID is required")
    private Long userId;
}