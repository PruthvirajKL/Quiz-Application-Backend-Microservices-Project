package com.Microservices.question_service.service;

import com.Microservices.question_service.dao.Questiondao;
import com.Microservices.question_service.model.QuestionWrapper;
import com.Microservices.question_service.model.Questions;
import com.Microservices.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Questionservice {

    @Autowired
    Questiondao questiondao;

    public ResponseEntity<List<Questions>> getAllquestions() {
        try {
            return new ResponseEntity<>(questiondao.findAll(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Questions>> getquestionbycategory(String category) {
       try {
           return new ResponseEntity<>(questiondao.findBycategory(category),HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Questions questions) {
        questiondao.save(questions);
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(int id, Questions questions) {
        try {
            Optional<Questions> findQuestion=questiondao.findById(id);
            if ((findQuestion.isPresent())){
                Questions existQuestion=findQuestion.get();

                existQuestion.setCategory(questions.getCategory());
                existQuestion.setDifficultylevel(questions.getDifficultylevel());
                existQuestion.setOption1(questions.getOption1());
                existQuestion.setOption2(questions.getOption2());
                existQuestion.setOption3(questions.getOption3());
                existQuestion.setOption4(questions.getOption4());
                existQuestion.setQuestion_title(questions.getQuestion_title());
                existQuestion.setRight_answer(questions.getRight_answer());

                questiondao.save(existQuestion);
                return new ResponseEntity<>("Updated Successful",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Question Not found",HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error Question Not Found",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            Optional<Questions> findQuestion = questiondao.findById(id);
            if (findQuestion.isPresent()) {
                questiondao.deleteById(id);
                return new ResponseEntity<>("Deleted Successful", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions=questiondao.findRandomQuestionsByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Questions> questions = new ArrayList<>();

        for (Integer id: questionIds){
            questions.add(questiondao.findById(id).get());
        }

        for (Questions question:questions){
            QuestionWrapper wrapper=new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestion_title(question.getQuestion_title());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right=0;
        for(Response response : responses){
            Questions questions=questiondao.findById(response.getId()).get();
            if (response.getResponse().equals(questions.getRight_answer())){
                right++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
