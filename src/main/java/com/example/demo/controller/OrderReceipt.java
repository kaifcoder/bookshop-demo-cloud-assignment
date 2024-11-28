package com.example.demo.controller;

import com.example.demo.entity.Book;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderReceipt {
    private String customerName;
    private String customerPhone;
    private List<Book> items;
    private String totalPrice;
    private String orderDateTime;

    // Getters, setters, and constructors
}
