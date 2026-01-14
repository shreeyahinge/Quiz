package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultController {

    @GetMapping("/result")
    public String showResult(@RequestParam int score, Model model) {
        model.addAttribute("totalScore", score);
        return "result"; // Looks for result.html in templates
    }
}
