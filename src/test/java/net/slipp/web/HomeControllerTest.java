package net.slipp.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import support.test.BasicAuthAcceptanceTest;

public class HomeControllerTest extends BasicAuthAcceptanceTest {
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
}
