package com.tka.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tka.model.Candidate;
import com.tka.model.Question;
import com.tka.model.Result;
import com.tka.service.AnswerService;
import com.tka.service.CandidateService;
import com.tka.service.QuestionService;
import com.tka.service.ResultService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainTestController {

	@Autowired
	QuestionService questionService;

	@Autowired
	CandidateService candidateService;

	@Autowired
	AnswerService answerService;

	@Autowired
	ResultService resultService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/save-candidate")
	public String saveCandidate(Candidate c, Model model) {
		Candidate saveCandidate = candidateService.saveCandidate(c); // name, email, mobile
		model.addAttribute("msg", "Candidate Registered Successfully! ID: " + saveCandidate.getCid());
		model.addAttribute("cid", saveCandidate.getCid());
		return "startTest";
	}

	@GetMapping("/start-test")
	public String startTestForm(Model model) {
		model.addAttribute("candidate", new Candidate());
		return "startTest"; // startTest.jsp
	}

	@PostMapping("/begin-test")
	public String beginTest(@ModelAttribute("candidate") Candidate candidate, BindingResult bindingResult, Model model,
			HttpSession session) {
		Optional<Candidate> existing = candidateService.findByName(candidate.getName());
		Candidate saved;
		if (existing.isPresent()) {
			saved = existing.get();
		} else {
			saved = candidateService.saveCandidate(candidate);
		}
		session.setAttribute("candidateName", saved.getName());
		session.setAttribute("candidateId", saved.getCid());
		List<Question> questions = questionService.getAllQuestions();
		model.addAttribute("cid", saved.getCid());
		model.addAttribute("questions", questions);
		return "testPage";
	}

	@PostMapping("/submit-test")
	public String submitTest(@RequestParam Map<String, String> formData, HttpSession session, Model model) {
		Object nameObj = session.getAttribute("candidateName");
		Object idObj = session.getAttribute("candidateId");
		if (nameObj == null || idObj == null) {
			model.addAttribute("error", "Session expired. Please restart the test.");
			return "startTest";
		}
		int cid = (int) idObj;
		List<Question> questions = questionService.getAllQuestions();
		Map<Integer, String> answersMap = new HashMap<>();
		for (Question q : questions) {
			String key = "q" + q.getQid();
			String answer = formData.get(key);
			if (answer != null)
				answer = answer.trim();
			answersMap.put(q.getQid(), answer);
		}
		questionService.saveStudentAnswers(answersMap, cid);
		int score = questionService.calculateScore(answersMap);
		int total = questions.size();
		int percent = total == 0 ? 0 : (score * 100) / total;
		String status = percent >= 40 ? "PASS" : "FAIL";
		// Save result (initial, draftedBy null)
		Result result = new Result();
		result.setCid(cid);
		result.setScore(score);
		result.setTotalQuestions(total);
		result.setPercent(percent);
		result.setStatus("PENDING"); // moderator will finalize
		result.setDraftedBy("AUTO"); // auto-draft
		resultService.saveResult(result);
		model.addAttribute("score", score);
		model.addAttribute("total", total);
		model.addAttribute("percent", percent);
		model.addAttribute("status", "PENDING - Moderator will verify");
		return "resultPage";
	}
}
