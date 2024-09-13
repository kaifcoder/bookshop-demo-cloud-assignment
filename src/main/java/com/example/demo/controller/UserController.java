//package com.example.demo.controller;
//
//import com.example.demo.entity.User;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute User user) {
//        userService.registerUser(user);
//        return "redirect:/login";
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }
//}
