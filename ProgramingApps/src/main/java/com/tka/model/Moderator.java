package com.tka.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Component
@Entity
public class Moderator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int moderatorId;
	private String moderatorName;
	private String email;
	private String mobile;
	
	public int getCid() {
		return moderatorId;
	}
	public void setCid(int cid) {
		this.moderatorId = cid;
	}
	public String getModeratorName() {
		return moderatorName;
	}
	public void setModeratorName(String moderatorName) {
		this.moderatorName = moderatorName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}