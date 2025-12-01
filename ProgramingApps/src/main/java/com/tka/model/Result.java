package com.tka.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Component
@Entity
@Table(name = "results")
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rid;
	private int cid; // Candidate ID
	private int score; // Obtained score
	private int totalQuestions; // Total questions in test
	private double percent; // Percentage score
	private String status; // APPROVED / REJECTED / PENDING
	private String draftedBy; // "AUTO" or moderator username

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDraftedBy() {
		return draftedBy;
	}

	public void setDraftedBy(String draftedBy) {
		this.draftedBy = draftedBy;
	}

	@Override
	public String toString() {
		return "Result [rid=" + rid + ", cid=" + cid + ", score=" + score + ", totalQuestions=" + totalQuestions
				+ ", percent=" + percent + ", status=" + status + ", draftedBy=" + draftedBy + "]";
	}
}
