package com.example.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    // List all books
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    
    // Show form to add a new book
    @GetMapping("/add")
    public String showAddForm(Book book) {
        return "add-book";
    }
    
    // Handle add book
    @PostMapping("/add")
    public String addBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }
        bookService.saveBook(book);
        return "redirect:/books";
    }
    
    // Show form to edit a book
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        
        model.addAttribute("book", book);
        return "update-book";
    }
    
    // Handle update book
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") long id, @Valid Book book, 
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }
        
        bookService.saveBook(book);
        return "redirect:/books";
    }
    
    // Delete a book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        bookService.deleteBook(book.getId());
        return "redirect:/books";
    }
}
