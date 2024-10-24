package com.Microservices.quiz_service.model;

import lombok.Data;

@Data
public class QuizDto {
    String categoryName;
    int numQuestions;
    String title;
}
