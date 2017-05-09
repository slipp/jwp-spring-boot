package net.slipp.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.UnAuthorizedException;
import net.slipp.domain.Question;
import net.slipp.domain.User;
import net.slipp.security.LoginUser;
import net.slipp.service.QnaService;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Resource(name = "qnaService")
	private QnaService qnaService;

	@GetMapping("/form")
	public String form(@LoginUser User loginUser) {
		return "/qna/form";
	}
	
	@PostMapping("")
	public String create(@LoginUser User loginUser, Question question) {
		qnaService.create(loginUser, question);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		model.addAttribute("question", qnaService.findById(id));
		return "/qna/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
		Question question = qnaService.findById(id);
		if (!question.isOwner(loginUser)) {
			throw new UnAuthorizedException();
		}
		
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable long id, Question question) {
		Question updatedQuestion = qnaService.update(loginUser, id, question);
		return "redirect:" + updatedQuestion.toUrl();
	}
}
