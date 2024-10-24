package com.Microservices.quiz_service.cotroller;


import com.Microservices.quiz_service.model.QuestionWrapper;
import com.Microservices.quiz_service.model.QuizDto;
import com.Microservices.quiz_service.model.Response;
import com.Microservices.quiz_service.service.Quizservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class Quizcontroller {

    @Autowired
    Quizservice quizservice;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizservice.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions(),quizDto.getTitle());
    }

    @PostMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizservice.getQuizQuestion(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizservice.calculateResult(id,responses);
    }
}
