package support.test;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.runner.RunWith;
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
public class RestAssuredIntegrationTest {
	@Value("${local.server.port}")
	private int serverPort;
	
	@Autowired
	private UserRepository userRepository;
	
	private User loginUser;
	
	@Before
	public void setup() {
		RestAssured.port = serverPort;
		loginUser = userRepository.findByUserId("javajigi").get();
	}
	
	protected RequestSpecification given_auth_json() {
		return given()
					.auth().preemptive().basic(loginUser.getUserId(), loginUser.getPassword())
					.contentType(ContentType.JSON);
	}
}
