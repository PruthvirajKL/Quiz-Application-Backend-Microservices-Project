package com.Microservices.quiz_service.service;


import com.Microservices.quiz_service.dao.Quizdao;
import com.Microservices.quiz_service.feign.QuizInterface;
import com.Microservices.quiz_service.model.QuestionWrapper;
import com.Microservices.quiz_service.model.Quiz;
import com.Microservices.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class Quizservice {

    @Autowired
    Quizdao quizdao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizdao.save(quiz);

        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(int id) {

              Quiz quiz=quizdao.findById(id).get();
              List<Integer> questionIds = quiz.getQuestionIds();

              ResponseEntity<List<QuestionWrapper>> questions =  quizInterface.getquestionsFromId(questionIds);

              return questions;

          }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
