package com.tka.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tka.dao.AnswerDao;
import com.tka.dao.QuestionDao;
import com.tka.model.Answer;
import com.tka.model.Question;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDao;
	@Autowired
	AnswerDao answerDao;

	public List<Question> getAllQuestions() {
		List<Question> questions = questionDao.findAll();
		int size = questions.size();
		int fromIndex = Math.max(size - 5, 0);
		List<Question> lastFive = questions.subList(fromIndex, size);
		questions = new ArrayList<>(lastFive);
		return questions;
	}

	public Question saveQuestion(Question q) {
		return questionDao.save(q);
	}

	public Optional<Question> getById(int qid) {
		return questionDao.findById(qid);
	}

	public void saveStudentAnswers(Map<Integer, String> answersMap, int cid) {
		answersMap.forEach((qid, answerText) -> {
			Answer a = new Answer();
			a.setCid(cid);
			a.setQid(qid);
			a.setAnswerText(answerText == null ? "" : answerText.trim());
			answerDao.save(a);
		});
	}

	public int calculateScore(Map<Integer, String> answersMap) {
		int score = 0;
		for (Map.Entry<Integer, String> e : answersMap.entrySet()) {
			Integer qid = e.getKey();
			String studentAnswer = e.getValue() == null ? "" : e.getValue().toLowerCase();
			Optional<Question> qOpt = questionDao.findById(qid);
			if (!qOpt.isPresent())
				continue;
			String keywords = qOpt.get().getExpectedKeywords();
			if (keywords == null || keywords.trim().isEmpty())
				continue;
			String[] ks = keywords.toLowerCase().split(",");
			int match = 0;
			for (String k : ks) {
				if (k.trim().isEmpty())
					continue;
				if (studentAnswer.contains(k.trim()))
					match++;
			}
			if (match > 0)
				score++;
		}
		return score;
	}

	public void deleteById(int id) {

	}

	public Map<Integer, String> getAnswers(List<Question> questions, Map<String, String> formData) {

		Map<Integer, String> answersMap = new HashMap<>();

		for (Question q : questions) {
			String key = "q" + q.getQid(); // Example: q1, q2, q3...
			String answer = formData.get(key);

			if (answer != null)
				answer = answer.trim();

			answersMap.put(q.getQid(), answer);
		}

		return answersMap;
	}

}