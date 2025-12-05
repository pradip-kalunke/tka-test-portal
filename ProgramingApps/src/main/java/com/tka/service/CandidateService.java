package com.tka.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tka.dao.CandidateDao;
import com.tka.model.Candidate;

@Service
public class CandidateService {

	@Autowired
	private CandidateDao candidateDao;

	public Candidate saveCandidate(Candidate candidate) {
		return candidateDao.save(candidate);
	}

	public int saveCandidateReturnId(String name) {
		Candidate c = new Candidate();
		c.setName(name.trim()); // small improvement
		return candidateDao.save(c).getCid();
	}

	public Optional<Candidate> findByName(String name) {
		return candidateDao.findByName(name);
	}

	public Optional<Candidate> findById(Integer cid) {
		return candidateDao.findById(cid);
	}

	public List<Candidate> findAll() {
		return candidateDao.findAll();
	}

	public Candidate getById(int cid) {
		return candidateDao.findById(cid).orElse(null);
	}

}
