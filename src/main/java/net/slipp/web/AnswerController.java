package net.slipp.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.User;
import net.slipp.security.LoginUser;
import net.slipp.service.QnaService;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	@Resource(name = "qnaService")
	private QnaService qnaService;
	
	@PostMapping("")
	public String create(@LoginUser User loginUser, @PathVariable long questionId, String contents) {
		Question question = qnaService.addAnswer(loginUser, questionId, contents);
		return "redirect:" + question.generateUrl();
	}
	
	@DeleteMapping("/{id}")
	public String delete(@LoginUser User loginUser, @PathVariable long questionId, @PathVariable long id) {
		qnaService.deleteAnswer(loginUser, id);
		return String.format("redirect:/questions/%d", questionId);
	}
}
