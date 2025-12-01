package com.tka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tka.model.Moderator;

@Repository
public interface ModeratorDao extends JpaRepository<Moderator, Integer> {

	Moderator findByEmail(String email);
	Moderator findByEmailAndMobile(String email, String mobile);
}