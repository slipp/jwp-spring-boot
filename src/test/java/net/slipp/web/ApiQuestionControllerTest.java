package net.slipp.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.slipp.domain.Question;
import support.test.BasicAuthIntegrationTest;

public class ApiQuestionControllerTest extends BasicAuthIntegrationTest {
	private static final Logger log = LoggerFactory.getLogger(ApiQuestionControllerTest.class);

	@Test
	public void create_entity() throws Exception {
		Question question = new Question("TDD는 의미있는 활동인가?", "당근 엄청 의미있는 활동이고 말고..");
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/api/questions", question, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void create_invalid() throws Exception {
		Question question = new Question("T", "T");
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/api/questions", question, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void create_object() throws Exception {
		Question question = new Question("TDD는 의미있는 활동인가?", "당근 엄청 의미있는 활동이고 말고..");
		Question createdQuestion = basicAuthTemplate.postForObject("/api/questions", question, Question.class);
		log.debug("created question : {}", createdQuestion);
	}
}
