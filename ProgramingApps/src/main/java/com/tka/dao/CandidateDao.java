package com.tka.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tka.model.Candidate;

@Repository
public interface CandidateDao  extends JpaRepository<Candidate, Integer>{

    
   //Candidate findByCname(String cname);

    Candidate findByEmail(String email);

    Candidate findByMobile(String mobile);

	Optional<Candidate> findByName(String name);

}
