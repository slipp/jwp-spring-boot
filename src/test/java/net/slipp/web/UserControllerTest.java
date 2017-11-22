package net.slipp.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @Before
    public void setup() {
        testUser = userRepository.save(new User("sanjigi", "password", "name", "javajigi@slipp.net"));
    }

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/users/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("userId", "javajigi");
        params.add("password", "pass");
        params.add("name", "재성");
        params.add("email", "javajigi@slipp.net");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params,
                headers);
        ResponseEntity<String> response = template.postForEntity("/users", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        log.debug("created user : {}", userRepository.findByUserId("javajigi"));
        assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
    }

    @Test
    public void list() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/users", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
        assertThat(response.getBody().contains(testUser.getEmail()), is(true));
    }

    @Test
    public void updateForm() throws Exception {
        ResponseEntity<String> response = template.getForEntity(String.format("/users/%d/form", testUser.getId()),
                String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains(testUser.getEmail()), is(true));
    }

    @Test
    public void update() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("_method", "put");
        params.add("password", "pass2");
        params.add("name", "재성2");
        params.add("email", "javajigi2@slipp.net");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params,
                headers);
        ResponseEntity<String> response = template.postForEntity(String.format("/users/%d", testUser.getId()), request,
                String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
    }

    @After
    public void tearDown() {
        userRepository.delete(testUser);
    }
}
