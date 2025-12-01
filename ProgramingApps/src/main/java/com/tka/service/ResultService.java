package com.tka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tka.dao.ResultDao;
import com.tka.model.Result;

@Service
public class ResultService {

	@Autowired
	private ResultDao resultDao;

	public Result saveResult(Result r) {
		// r.setUpdatedAt(LocalDateTime.now());
		return resultDao.save(r);
	}

	public Optional<Result> findByCid(int cid) {
		return resultDao.findByCid(cid);
	}

	public List<Result> findAll() {
		return resultDao.findAll();
	}

}
