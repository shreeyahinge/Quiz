package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;

@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    QuestionRepository repo;

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "teacher-dashboard"; // Serves teacher-dashboard.html
    }

    @PostMapping("/addQuestion")
    public String addQuestion(@ModelAttribute Question question) {
        repo.save(question);
        return "redirect:/teacher/dashboard"; // Redirect back after saving
    }
}
