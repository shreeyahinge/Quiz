package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    QuestionRepository repo;

    @PostMapping("/addQuestion")
    public String addQuestion(@ModelAttribute Question question) {
        repo.save(question);
        return "redirect:/teacher/dashboard"; // Redirect back after saving
    }
}
