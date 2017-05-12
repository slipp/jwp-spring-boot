package net.slipp.web;

import org.junit.Test;

import net.slipp.domain.Question;
import support.test.RestAssuredIntegrationTest;

public class ApiRAQuestionControllerTest extends RestAssuredIntegrationTest {
	@Test
	public void create() throws Exception {
		Question question = new Question("TDD는 의미있는 활동인가?", "당근 엄청 의미있는 활동이고 말고..");
		given_auth_json()
			.body(question)
        .when()
        	.post("/api/questions")
        .then()
        	.statusCode(201);
	}
}
