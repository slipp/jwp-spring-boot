package net.slipp.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {
    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @Before
    public void setup() {
        this.testUser = userRepository.save(new User("sanjigi", "password", "name", "javajigi@slipp.net"));
    }

    @Test
    public void login_success() throws Exception {
        ResponseEntity<String> response = login(testUser.getUserId(), testUser.getPassword());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertTrue(response.getHeaders().getLocation().getPath().startsWith("/;jsessionid="));
    }

    @Test
    public void login_not_found_user() throws Exception {
        ResponseEntity<String> response = login(testUser.getUserId() + "1", testUser.getPassword());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요."), is(true));
    }

    @Test
    public void login_mismatch_password() throws Exception {
        ResponseEntity<String> response = login(testUser.getUserId(), testUser.getPassword() + "1");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요."), is(true));
    }

    private ResponseEntity<String> login(String userId, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("userId", userId);
        params.add("password", password);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params,
                headers);
        ResponseEntity<String> response = template.postForEntity("/login", request, String.class);
        return response;
    }

    @After
    public void tearDown() {
        userRepository.delete(testUser);
    }
}
