package net.slipp.web;

import net.slipp.domain.Answer;
import net.slipp.domain.User;
import net.slipp.security.LoginUser;
import net.slipp.service.QnaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Resource(name = "qnaService")
	private QnaService qnaService;

	@PostMapping("")
	public ResponseEntity<Void> addAnswer(@LoginUser User loginUser,
									@PathVariable long questionId,
									@Valid @RequestBody Answer answer) {
		Answer savedAnswer = qnaService.addAnswer(loginUser, questionId, answer.getContents());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/api" + savedAnswer.generateUrl()));
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
}
