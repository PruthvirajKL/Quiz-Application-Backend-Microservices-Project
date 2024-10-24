package com.Microservices.question_service.cotroller;

import com.Microservices.question_service.model.QuestionWrapper;
import com.Microservices.question_service.model.Questions;
import com.Microservices.question_service.model.Response;
import com.Microservices.question_service.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class Questioncontroller {

    @Autowired
    Questionservice questionservice;

    @Autowired
    Environment environment;

    @GetMapping("allquestions")
   public ResponseEntity<List<Questions>> getAllquestions(){
        return questionservice.getAllquestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Questions>> getquestionbycategory(@PathVariable String category){
        return questionservice.getquestionbycategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Questions questions){
        return questionservice.addQuestion(questions);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable int id,@RequestBody Questions questions){
        return questionservice.updateQuestion(id,questions);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionservice.deleteQuestion(id);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,@RequestParam Integer numQuestions){
        return questionservice.getQuestionsForQuiz(categoryName,numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getquestionsFromId(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionservice.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionservice.getScore(responses);
    }
}
