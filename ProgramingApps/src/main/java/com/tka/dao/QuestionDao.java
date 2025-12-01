package com.tka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tka.model.Question;

@Repository
public interface QuestionDao  extends JpaRepository<Question, Integer>{

}
