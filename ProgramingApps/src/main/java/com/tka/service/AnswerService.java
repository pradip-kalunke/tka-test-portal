package com.tka.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tka.dao.AnswerDao;
import com.tka.dao.QuestionDao;
import com.tka.dto.QuestionAnswerView;
import com.tka.model.Answer;
import com.tka.model.Question;

@Service
public class AnswerService {

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private QuestionDao questionDao;

	public List<Answer> getAnswersByCandidate(int cid) {
		return answerDao.findByCid(cid);
	}

	public List<QuestionAnswerView> getAnswersWithQuestions(int cid) {
		List<Question> questions = questionDao.findAll();
		List<Answer> answers = answerDao.findByCid(cid);
		Map<Integer, String> ansMap = answers.stream().collect(Collectors.toMap(Answer::getQid, Answer::getAnswerText));
		return questions.stream().map(q -> new QuestionAnswerView(q.getQid(), q.getQuestionText(),
				q.getExpectedKeywords(), ansMap.getOrDefault(q.getQid(), ""))).collect(Collectors.toList());
	}

	public void updateAnswerAndMarks(int cid, int qid, String answerText, int marks) {
		Answer ans = answerDao.findByCidAndQid(cid, qid).orElse(new Answer());
		ans.setCid(cid);
		ans.setQid(qid);
		ans.setAnswerText(answerText);
		ans.setMarks(marks);
		answerDao.save(ans);
	}

}
