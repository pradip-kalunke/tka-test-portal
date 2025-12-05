package com.tka.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tka.dao.AnswerDao;
import com.tka.dao.QuestionDao;
import com.tka.dto.QuestionAnswerView;
import com.tka.model.Answer;
import com.tka.model.Question;
import com.tka.model.Result;

@Service
public class ModeratorService {

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private ResultService resultService;

	public List<QuestionAnswerView> getAnswersWithQuestions(int cid) {

		List<Question> questions = questionDao.findAll();
		List<Answer> answers = answerDao.findByCid(cid);

		Map<Integer, Answer> ansMap = answers.stream()
		        .collect(Collectors.toMap(
		                Answer::getQid,
		                a -> a,
		                (a1, a2) -> a1   // keep first answer, ignore duplicates
		        ));

		List<QuestionAnswerView> qaList = new ArrayList<>();

		for (Question q : questions) {

			Answer ans = ansMap.get(q.getQid());

			QuestionAnswerView view = new QuestionAnswerView();
			view.setQid(q.getQid());
			view.setQuestionText(q.getQuestionText());
			view.setExpectedKeywords(q.getExpectedKeywords());

			if (ans != null) {
				view.setAnswerText(ans.getAnswerText());
				view.setMarks(ans.getMarks());
			} else {
				view.setAnswerText("");
				view.setMarks(0);
			}

			qaList.add(view);
		}

		return qaList;
	}

	public void updateAnswerAndMarks(int cid, int qid, String answerText, int marks) {
		List<Answer> list = answerDao.findByCidAndQid(cid, qid);
		Answer existing = list.isEmpty() ? null : list.get(0);
		if (existing == null) {
			existing = new Answer();
			existing.setCid(cid);
			existing.setQid(qid);
		}
		existing.setAnswerText(answerText);
		existing.setMarks(marks);
		answerDao.save(existing);
	}

	public void updateCandidateResult(int cid) {
		List<Answer> answers = answerDao.findByCid(cid);
		int totalScore = answers.stream()
		        .map(Answer::getMarks)
		        .filter(Objects::nonNull)
		        .mapToInt(Integer::intValue)
		        .sum();
		int totalQuestions = questionDao.findAll().size();
		Result result = resultService.findByCid(cid).orElse(new Result());
		result.setCid(cid);
		result.setScore(totalScore);
		result.setTotalQuestions(totalQuestions);
		int percent = (totalQuestions == 0) ? 0 : (totalScore * 100 / totalQuestions);
		result.setPercent(percent);
		result.setStatus(percent >= 40 ? "PASS" : "FAIL");
		result.setDraftedBy("system-auto");
		resultService.saveResult(result);
	}
}
