package com.tka.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tka.model.Result;

@Repository
public interface ResultDao extends JpaRepository<Result, Integer> {
	Optional<Result> findByCid(Integer cid);
}
