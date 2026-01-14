package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Response;
import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.service.QuizService;

@RestController
@RequestMapping("/api/quiz")
public class QuizRestController {

    @Autowired
    QuizService quizService;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @PostMapping("/submit")
    public int submitQuiz(@RequestBody List<Response> responses) {
        // This takes the list from JS and returns the final integer score
        return quizService.calculateResult(responses);
    }
}