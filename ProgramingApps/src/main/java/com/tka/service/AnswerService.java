package com.tka.service;

import java.util.ArrayList;
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

	    // Convert list of answers to map by qid (easy lookup)
	    Map<Integer, Answer> ansMap = answers.stream()
	    		.collect(Collectors.toMap(
	    		        Answer::getQid,
	    		        a -> a,
	    		        (existing, duplicate) -> existing   // KEEP FIRST
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
	            view.setMarks(ans.getMarks());          // <-- IMPORTANT
	        } else {
	            view.setAnswerText("");
	            view.setMarks(0);                       // default
	        }

	        qaList.add(view);
	    }

	    return qaList;
	}

	public void updateAnswerAndMarks(int cid, int qid, String answerText, int marks) {
	    List<Answer> list = answerDao.findByCidAndQid(cid, qid);
	    Answer ans = list.isEmpty() ? new Answer() : list.get(0);
	    ans.setCid(cid);
	    ans.setQid(qid);
	    ans.setAnswerText(answerText);
	    ans.setMarks(marks);
	    answerDao.save(ans);
	}


	public int calculateTotalScore(int cid) {
	    return answerDao.sumMarksByCid(cid);
	}

	public void updateCandidateResult(int cid) {
		// TODO Auto-generated method stub
		
	}

}
