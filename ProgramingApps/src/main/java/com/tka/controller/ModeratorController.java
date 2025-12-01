package com.tka.controller;

import java.util.List;
import java.util.Optional;

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
	CandidateService candidateService;
	@Autowired
	AnswerService answerService;
	@Autowired
	QuestionService questionService;
	@Autowired
	ResultService resultService;

	@GetMapping("/")
	public String listCandidatesCopy(Model model) {
		model.addAttribute("candidates", candidateService.findAll());
		return "moderator/listCandidates"; // listCandidates.jsp
	}

	@GetMapping("/candidates")
	public String listCandidates(Model model) {
		model.addAttribute("candidates", candidateService.findAll());
		return "moderator/listCandidates"; // listCandidates.jsp
	}

	@GetMapping("/view-answers/{cid}")
	public String viewAnswers(@PathVariable int cid, Model model) {
		Candidate candidate = candidateService.findById(cid).orElse(null);
		List<QuestionAnswerView> qaList = answerService.getAnswersWithQuestions(cid);
		model.addAttribute("candidate", candidate);
		model.addAttribute("qaList", qaList); // ← FIXED: JSP expects qaList
		return "moderator/viewAnswers";
	}

	@GetMapping("/update-answers/{cid}")
	public String updateAnswersView(@PathVariable int cid, Model model) {
		Candidate candidate = candidateService.getById(cid);
		List<QuestionAnswerView> answers = answerService.getAnswersWithQuestions(cid);
		model.addAttribute("candidate", candidate);
		model.addAttribute("answers", answers);
		return "moderator/updateAnswers";
	}

	@PostMapping("/save-updated-answers")
	public String saveUpdatedAnswers(@RequestParam int cid, HttpServletRequest request) {
		List<Question> questions = questionService.getAllQuestions();
		for (Question q : questions) {
			String ansKey = "ans_" + q.getQid();
			String markKey = "mark_" + q.getQid();
			String updatedAnswer = request.getParameter(ansKey);
			String markStr = request.getParameter(markKey);
			int marks = (markStr != null) ? Integer.parseInt(markStr) : 0;
			answerService.updateAnswerAndMarks(cid, q.getQid(), updatedAnswer, marks);
		}
		return "redirect:/moderator/view-answers/" + cid;
	}

	// Moderator drafts/edits result (manual override)
	@PostMapping("/draft-result")
	public String draftResult(@RequestParam Integer cid, @RequestParam Integer score,
			@RequestParam(required = false) String status, @RequestParam(required = false) String draftedBy,
			RedirectAttributes ra) {
		Optional<Result> existing = resultService.findByCid(cid);
		Result r;
		int totalQ = questionService.getAllQuestions().size();
		if (existing.isPresent()) {
			r = existing.get();
		} else {
			r = new Result();
			r.setCid(cid);
		}
		r.setScore(score);
		r.setTotalQuestions(totalQ);
		int percent = totalQ == 0 ? 0 : (score * 100) / totalQ;
		r.setPercent(percent);
		r.setStatus(status == null ? (percent >= 40 ? "PASS" : "FAIL") : status);
		r.setDraftedBy(draftedBy == null ? "moderator" : draftedBy);
		resultService.saveResult(r);

		ra.addFlashAttribute("msg", "Result drafted/updated successfully.");
		return "redirect:/moderator/view-answers/" + cid;
	}

	// Moderator can finalize status explicitly
	@PostMapping("/update-status")
	public String updateStatus(@RequestParam Integer cid, @RequestParam String status, RedirectAttributes ra) {
		Optional<Result> res = resultService.findByCid(cid);
		if (!res.isPresent()) {
			ra.addFlashAttribute("error", "Result not found to update status.");
			return "redirect:/moderator/candidates";
		}
		Result r = res.get();
		r.setStatus(status);
		resultService.saveResult(r);
		ra.addFlashAttribute("msg", "Status updated.");
		return "redirect:/moderator/view-answers/" + cid;
	}
}
