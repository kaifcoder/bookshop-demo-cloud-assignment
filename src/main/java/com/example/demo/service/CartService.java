package com.example.demo.service;

import com.example.demo.entity.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final List<Book> cart = new ArrayList<>();

    public void addBookToCart(Book book) {
        cart.add(book);
    }

    public List<Book> getCartItems() {
        return new ArrayList<>(cart);
    }

    public void clearCart() {
        cart.clear();
    }
}