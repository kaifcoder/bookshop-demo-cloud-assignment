package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.text.DecimalFormat;
import java.util.List;

@Controller
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

    @GetMapping("/add/{id}")
    public String addBookToCartt(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        cartService.addBookToCart(book);
        return "redirect:/";
    }
    // add through thymeleaf
    @PostMapping("/add/{id}")
    public String addBookToCart(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        cartService.addBookToCart(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String getCartItems(Model model) {
        List<Book> cartItems = cartService.getCartItems();
        double totalPrice = cartItems.stream().mapToDouble(Book::getPrice).sum();
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedTotalPrice = df.format(totalPrice);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", formattedTotalPrice);
        return "cart";
    }

    @GetMapping("/remove/{id}")
    public String removeBookFromCart(@PathVariable("id") long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        cartService.removeBookFromCart(book);
        return "redirect:/";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/";
    }
}