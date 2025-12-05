package com.tka.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tka.model.Answer;

@Repository
public interface AnswerDao extends JpaRepository<Answer, Integer> {

	List<Answer> findByCidAndQid(int cid, int qid);

	List<Answer> findByCid(Integer cid);

	@Query("SELECT COALESCE(SUM(a.marks), 0) FROM Answer a WHERE a.cid = :cid")
	int sumMarksByCid(int cid);
	
	@Query("SELECT a FROM Answer a WHERE a.cid = :cid AND a.qid = :qid")
	List<Answer> findByCidAndQid(@Param("cid") Integer cid, @Param("qid") Integer qid);


}