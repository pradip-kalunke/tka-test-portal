package com.tka.dto;

public class QuestionAnswerView {

	private int qid;
	private String questionText;
	private String expectedKeywords;
	private String answerText;
	private int marks; // ADD THIS

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public QuestionAnswerView() {
	}

	public QuestionAnswerView(int qid, String questionText, String expectedKeywords, String answerText) {
		this.qid = qid;
		this.questionText = questionText;
		this.expectedKeywords = expectedKeywords;
		this.answerText = answerText;
	}

	public int getQid() {
		return qid;
	}

	public String getQuestionText() {
		return questionText;
	}

	public String getExpectedKeywords() {
		return expectedKeywords;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public void setExpectedKeywords(String expectedKeywords) {
		this.expectedKeywords = expectedKeywords;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
}
