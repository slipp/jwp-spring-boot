package net.slipp.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import support.test.BasicAuthIntegrationTest;
import support.test.HtmlFormDataBuilder;

public class QuestionControllerTest extends BasicAuthIntegrationTest {
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
}
