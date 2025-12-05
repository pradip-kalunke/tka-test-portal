package com.tka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tka.dto.QuestionAnswerView;
import com.tka.model.Candidate;
import com.tka.model.Question;
import com.tka.model.Result;
import com.tka.service.AnswerService;
import com.tka.service.CandidateService;
import com.tka.service.QuestionService;
import com.tka.service.ResultService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private ResultService resultService;

	@GetMapping("/")
	public String getAllCandidates(Model model) {
		List<Candidate> candidates = candidateService.findAll();
		model.addAttribute("candidates", candidates);
		return "/moderator/listCandidates";
	}

	// Show answers to moderator
	@GetMapping("/view-answers/{cid}")
	public String viewAnswers(@PathVariable int cid, Model model) {

		Candidate candidate = candidateService.findById(cid).orElse(null);

		List<QuestionAnswerView> qaList = answerService.getAnswersWithQuestions(cid);

		int totalScore = qaList.stream().mapToInt(QuestionAnswerView::getMarks).sum();

		model.addAttribute("candidate", candidate);
		model.addAttribute("qaList", qaList);
		model.addAttribute("totalScore", totalScore);

		return "moderator/viewAnswers";
	}

	// Update answers + marks page
	@GetMapping("/update-answers/{cid}")
	public String updateAnswers(@PathVariable int cid, Model model) {

		Candidate candidate = candidateService.findById(cid).orElse(null);
		List<QuestionAnswerView> answers = answerService.getAnswersWithQuestions(cid);

		model.addAttribute("candidate", candidate);
		model.addAttribute("answers", answers);

		return "moderator/updateAnswers";
	}

	// Save moderator updates
	@PostMapping("/save-updated-answers")
	public String saveUpdatedAnswers(@RequestParam int cid, HttpServletRequest request) {

		List<Question> questions = questionService.getAllQuestions();

		for (Question q : questions) {

			String ansKey = "ans_" + q.getQid();
			String markKey = "mark_" + q.getQid();

			String updatedAnswer = request.getParameter(ansKey);
			String markStr = request.getParameter(markKey);

			int marks = (markStr == null || markStr.isEmpty()) ? 0 : Integer.parseInt(markStr);

			answerService.updateAnswerAndMarks(cid, q.getQid(), updatedAnswer, marks);
		}

		// ⬇ Auto-update result
		answerService.updateCandidateResult(cid);

		return "redirect:/moderator/view-answers/" + cid;
	}

	// Manual draft override (optional)
	@PostMapping("/draft-result")
	public String draftResult(@RequestParam Integer cid, @RequestParam(required = false) Integer score,
			@RequestParam(required = false) String status, @RequestParam(required = false) String draftedBy,
			RedirectAttributes ra) {

		int totalQ = questionService.getAllQuestions().size();
		if (score == null)
			score = 0;

		Result result = resultService.findByCid(cid).orElse(new Result());
		result.setCid(cid);
		result.setScore(score);
		result.setTotalQuestions(totalQ);

		int percent = totalQ == 0 ? 0 : (score * 100) / totalQ;
		result.setPercent(percent);
		result.setStatus(status == null ? (percent >= 40 ? "PASS" : "FAIL") : status);
		result.setDraftedBy(draftedBy == null ? "moderator" : draftedBy);

		resultService.saveResult(result);

		ra.addFlashAttribute("msg", "Result drafted/updated successfully.");
		return "redirect:/moderator/view-answers/" + cid;
	}

}
