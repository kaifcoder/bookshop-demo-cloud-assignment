package com.example.demo.controller;

import com.example.demo.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;
    
    // List all books
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("cartSize", cartService.getCartItems().size());
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "books";
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
        return "redirect:/";
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
        return "redirect:/";
    }
    
    // Delete a book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        bookService.deleteBook(book.getId());
        return "redirect:/";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        // Get cart details
        List<Book> items = cartService.getCartItems();
        String total = cartService.getTotalPrice();

        // Add data to the model for checkout page
        model.addAttribute("items", items);
        model.addAttribute("total", total);

        // Return checkout view
        return "checkout";
    }

    @GetMapping("/confirmCheckout")
    public String confirm() {
        return "confirm";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam String customerName, @RequestParam String customerPhone, Model model) {
        // Save customer details and cart info (mock implementation or service layer)
        OrderReceipt receipt = generateReceipt(customerName, customerPhone);
        model.addAttribute("receipt", receipt);
        return "receipt"; // Render receipt page
    }
    public OrderReceipt generateReceipt(String name, String phone) {
        OrderReceipt receipt = new OrderReceipt();
//        get date and time in format dd mm yyyy hh mm am/pm

       String date = LocalDateTime.now().toString();
        receipt.setCustomerName(name);
        receipt.setCustomerPhone(phone);
        receipt.setItems(cartService.getCartItems());
        receipt.setTotalPrice(cartService.getTotalPrice());
        receipt.setOrderDateTime(
                date.substring(8, 10) + " " + date.substring(5, 7) + " " + date.substring(0, 4) + " " + date.substring(11, 16)
        );
        return receipt;
    }

    @DeleteMapping("/clear")
    @ResponseBody
    public void clearCart() {
        cartService.clearCart();
    }
}

