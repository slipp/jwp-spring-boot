package net.slipp.web;

import static net.slipp.domain.QuestionTest.createByLoginUser;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import support.test.BasicAuthIntegrationTest;
import support.test.HtmlFormDataBuilder;

public class QuestionControllerTest extends BasicAuthIntegrationTest {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	public void createForm_logout() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/questions/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));	
	}
	
	@Test
	public void createForm_login() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/questions/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}
	
	@Test
	public void create() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				.urlEncodedForm()
				.addParameter("title", "TDD는 의미있는 활동인가?")
				.addParameter("contents", "당근 엄청 의미있는 활동이고 말고..")
				.build();

		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/questions", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
	}
	
	@Test
	public void show() throws Exception {
		Question savedQuestion = questionRepository.save(createByLoginUser(loginUser));

		ResponseEntity<String> response = basicAuthTemplate.getForEntity(savedQuestion.toUrl(), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(savedQuestion.getTitle()));
		assertTrue(response.getBody().contains(savedQuestion.getContents()));
	}
	
	@Test
	public void updateForm_글쓴이() throws Exception {
		Question savedQuestion = questionRepository.save(createByLoginUser(loginUser));
		
		ResponseEntity<String> response = basicAuthTemplate.getForEntity(savedQuestion.toUrl() + "/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(savedQuestion.getTitle()));
		assertTrue(response.getBody().contains(savedQuestion.getContents()));
	}
	
	@Test
	public void updateForm_다른_사람이_쓴_글() throws Exception {
		Question savedQuestion = questionRepository.save(createByLoginUser(findByUserId("sanjigi")));
		
		ResponseEntity<String> response = basicAuthTemplate.getForEntity(savedQuestion.toUrl() + "/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
	}
	
	@Test
	public void update_글쓴이() throws Exception {
		Question savedQuestion = questionRepository.save(createByLoginUser(loginUser));
		
		String title = "TDD는 의미 없지 않나?";
		String contents = "맞아. TDD는 의심을 가지고 지켜봐야해.";
		
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				.urlEncodedForm()
				.addParameter("_method", "put")
				.addParameter("title", title)
				.addParameter("contents", contents)
				.build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(savedQuestion.toUrl(), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		Question updatedQuestion = questionRepository.findOne(savedQuestion.getId());
		assertThat(updatedQuestion.getTitle(), is(title));
		assertThat(updatedQuestion.getContents(), is(contents));
	}
	
	@Test
	public void update_다른_사람이_쓴_글() throws Exception {
		Question savedQuestion = questionRepository.save(createByLoginUser(findByUserId("sanjigi")));
		
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				.urlEncodedForm()
				.addParameter("_method", "put")
				.addParameter("title", "TDD는 의미 없지 않나?")
				.addParameter("contents", "맞아. TDD는 의심을 가지고 지켜봐야해.")
				.build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(savedQuestion.toUrl(), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
	}
}
