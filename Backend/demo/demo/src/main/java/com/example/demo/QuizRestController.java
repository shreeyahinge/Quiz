package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz")
public class QuizRestController {

    @Autowired
    QuizService quizService;

    @PostMapping("/submit")
    public int submitQuiz(@RequestBody List<Response> responses) {
        // This takes the list from JS and returns the final integer score
        return quizService.calculateResult(responses);
    }
}