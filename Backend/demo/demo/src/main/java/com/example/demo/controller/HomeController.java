package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // Serves home.html from templates folder
    }

    @GetMapping("/quiz")
    public String quiz() {
        return "quiz"; // Serves quiz.html from templates folder
    }
}
