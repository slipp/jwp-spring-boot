package net.slipp.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import net.slipp.domain.UserRepository;
import support.test.BasicAuthIntegrationTest;
import support.test.HtmlFormDataBuilder;

public class UserControllerTest extends BasicAuthIntegrationTest {
	private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

	@Autowired private UserRepository userRepository;
	
	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/users/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void create() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				.urlEncodedForm()
		        .addParameter("userId", "javajigi")
				.addParameter("password", "pass")
				.addParameter("name", "재성")
				.addParameter("email", "javajigi@slipp.net")
				.build();

		ResponseEntity<String> response = template.postForEntity("/users", request, String.class);
        
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(userRepository.findByUserId("javajigi"));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
	}
	
	@Test
	public void list() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/users", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
		assertThat(response.getBody().contains(loginUser.getEmail()), is(true));
	}
	
	@Test
	public void updateForm_no_login() throws Exception {
		ResponseEntity<String> response = template.getForEntity(String.format("/users/%d/form", loginUser.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
	}
	
	@Test
	public void updateForm_login() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity(String.format("/users/%d/form", loginUser.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains(loginUser.getEmail()), is(true));
	}
	
	@Test
	public void update_no_login() throws Exception {
		ResponseEntity<String> response = update(template);
		assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
	}
	
	private ResponseEntity<String> update(TestRestTemplate template) throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				.urlEncodedForm()
		        .addParameter("_method", "put")
				.addParameter("password", "pass2")
				.addParameter("name", "재성2")
				.addParameter("email", "javajigi@slipp.net")
				.build();
		
		return template.postForEntity(String.format("/users/%d", loginUser.getId()), request, String.class);
	}
	
	@Test
	public void update() throws Exception {
		ResponseEntity<String> response = update(basicAuthTemplate);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/users"));
	}
}	
