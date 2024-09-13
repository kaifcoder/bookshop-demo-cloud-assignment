package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public void addBookToCart(@RequestBody Book book) {
        cartService.addBookToCart(book);
    }

    // add through thymeleaf
    @PostMapping("/add/{id}")
    public void addBookToCart(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        cartService.addBookToCart(book);

    }

    @GetMapping("/items")
    public List<Book> getCartItems() {
        return cartService.getCartItems();
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
}