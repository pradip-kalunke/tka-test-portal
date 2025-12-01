package com.tka.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tka.model.Answer;

@Repository
public interface AnswerDao extends JpaRepository<Answer, Integer> {

	Optional<Answer> findByCidAndQid(int cid, int qid);

	List<Answer> findByCid(Integer cid);

	@Query("SELECT a FROM Answer a WHERE a.cid = :cid AND a.qid = :qid")
	Optional<Answer> findByCidAndQid(@Param("cid") Integer cid, @Param("qid") Integer qid);
}