package net.slipp.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;
import support.test.AbstractAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class LoginControllerTest extends AbstractAcceptanceTest {
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
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", userId).addParameter("password", password).build();
        ResponseEntity<String> response = template.postForEntity("/login", request, String.class);
        return response;
    }

    @After
    public void tearDown() {
        userRepository.delete(testUser);
    }
}
