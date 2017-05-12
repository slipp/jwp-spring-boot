package net.slipp.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.Answer;
import net.slipp.domain.User;
import net.slipp.security.LoginUser;
import net.slipp.service.QnaService;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Resource(name = "qnaService")
	private QnaService qnaService;

	@PostMapping("")
	@ResponseStatus( HttpStatus.CREATED )
	public Answer addAnswer(@LoginUser User loginUser, 
			@PathVariable long questionId, 
			@Valid @RequestBody Answer answer) {
		return qnaService.addAnswer(loginUser, questionId, answer.getContents());
	}
}
