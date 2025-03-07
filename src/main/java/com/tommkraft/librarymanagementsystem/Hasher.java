package com.tommkraft.librarymanagementsystem;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Hasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("password");
        System.out.println("New Hashed Password: " + hashedPassword);
    }
}