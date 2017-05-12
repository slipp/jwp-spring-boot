package net.slipp.web;

import java.net.URI;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<Void> create(@LoginUser User loginUser, 
			@Valid @RequestBody Question question) {
		Question savedQuestion = qnaService.create(loginUser, question);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api" + savedQuestion.generateUrl()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public Question show(@PathVariable long id) {
		return qnaService.findById(id);
	}
}
