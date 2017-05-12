package net.slipp.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.Question;
import net.slipp.domain.User;
import net.slipp.security.LoginUser;
import net.slipp.service.QnaService;

@RestController
@RequestMapping("/api/questions")
public class ApiQuestionController {
	@Resource(name = "qnaService")
	private QnaService qnaService;
	
	@PostMapping("")
	@ResponseStatus( HttpStatus.CREATED )
	public Question create(@LoginUser User loginUser, @Valid @RequestBody Question question) {
		return qnaService.create(loginUser, question);
	}
}
