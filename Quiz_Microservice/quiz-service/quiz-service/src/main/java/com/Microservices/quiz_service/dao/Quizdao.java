package com.Microservices.quiz_service.dao;


import com.Microservices.quiz_service.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Quizdao extends JpaRepository<Quiz,Integer> {
}
