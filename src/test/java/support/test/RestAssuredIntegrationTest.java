package support.test;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class RestAssuredIntegrationTest {
	private static final Logger log = LoggerFactory.getLogger(RestAssuredIntegrationTest.class);

	
	@Value("${local.server.port}")
	private int serverPort;
	
	@Autowired
	private UserRepository userRepository;
	
	protected User loginUser;
	
	@Before
	public void setup() {
		RestAssured.port = serverPort;
		loginUser = userRepository.findByUserId("javajigi").get();
	}

	protected RequestSpecification given_json() {
		return given()
				.contentType(ContentType.JSON);
	}
	
	protected RequestSpecification given_auth_json() {
		return given()
					.auth().preemptive().basic(loginUser.getUserId(), loginUser.getPassword())
					.contentType(ContentType.JSON);
	}
	
	protected RequestSpecification given_auth_json(String userId) {
	    User otherUser = userRepository.findByUserId(userId).get();
        return given()
                    .auth().preemptive().basic(otherUser.getUserId(), otherUser.getPassword())
                    .contentType(ContentType.JSON);
    }
	
	protected String createResource(String path, Object bodyPayload) {
		return createResource(path, bodyPayload, loginUser.getUserId());
	}
	
	protected String createResource(String path, Object bodyPayload, String userId) {
        String location = given_auth_json(userId)
            .body(bodyPayload)
            .when()
            .post(path)
            .then()
            .statusCode(201)
            .extract().header("location");
        log.debug("location : {}", location);
        return location;
    }
	
	protected <T> T getResource(String locationHeader, Class<T> responseClass) {
	    return given_auth_json()
	            .when()
                .get(locationHeader)
	            .then()
	            .statusCode(200)
	            .extract().as(responseClass);
	}

	protected <T> T getResources(String locationHeader, Class<T> responseClass) {
		return given_json()
				.when()
				.get(locationHeader)
				.then()
				.statusCode(200)
				.extract().as(responseClass);
	}

	protected <T> T getResources(String locationHeader, int page, int size, Class<T> responseClass) {
		return given_json()
				.param("page", page)
				.param("size", size)
				.when()
				.get(locationHeader)
				.then()
				.statusCode(200)
				.extract().as(responseClass);
	}
}
