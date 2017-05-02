package net.slipp.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import support.test.BasicAuthIntegrationTest;

public class HomeControllerTest extends BasicAuthIntegrationTest {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	public void home_logout() {
		ResponseEntity<String> response = template.getForEntity("/", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		String body = response.getBody();
		assertTrue(body.contains("회원가입") && body.contains("로그인"));
		assertFalse(body.contains("로그아웃") || body.contains("개인정보수정"));
	}

	@Test
	public void home_login() {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		String body = response.getBody();
		assertFalse(body.contains("회원가입") || body.contains("로그인"));
		assertTrue(body.contains("로그아웃") && body.contains("개인정보수정"));
	}
	
	@Test
	public void home_questions() throws Exception {
		Question question = new Question("this is the title", "this is my contents");
		question.writeBy(loginUser);
		questionRepository.save(question);
		
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(question.getTitle()));
	}
}
