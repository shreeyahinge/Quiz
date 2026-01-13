package com.yourproject.quiz.model;

import lombok.Data;

@Data // This automatically creates Getters and Setters if you have Lombok
public class Response {
    private Long id;        // This matches the "id" in your JavaScript
    private String response; // This matches the "response" (the selected option)
}