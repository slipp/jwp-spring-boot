package net.slipp.web;

import static net.slipp.domain.QuestionTest.createByLoginUser;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.slipp.domain.Answer;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import support.test.BasicAuthIntegrationTest;

public class ApiAnswerControllerTest extends BasicAuthIntegrationTest {
	private static final Logger log = LoggerFactory.getLogger(ApiAnswerControllerTest.class);

	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	public void create_entity() throws Exception {
		Question savedQuestion = questionRepository.save(createByLoginUser(loginUser));
		Answer answer = new Answer(loginUser, "하지만 TDD는 너무 하기 힘들 활동임다.");
		
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(
				String.format("/api/%s/answers", savedQuestion.generateUrl()), 
				answer, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		log.debug("body : {}", response.getBody());
	}
}
