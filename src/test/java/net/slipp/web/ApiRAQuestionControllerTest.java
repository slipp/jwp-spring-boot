package net.slipp.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import net.slipp.dto.QuestionDTO;
import support.test.RestAssuredIntegrationTest;

public class ApiRAQuestionControllerTest extends RestAssuredIntegrationTest {
	@Test
	public void create() throws Exception {
		QuestionDTO newQuestion = new QuestionDTO()
				.setTitle("TDD는 의미있는 활동인가?")
				.setContents("당근 엄청 의미있는 활동이고 말고..");
		
		String location = createResource("/api/questions", newQuestion);
		
		QuestionDTO actual = getResource(location, QuestionDTO.class);
		
		assertThat(actual.getTitle()).isEqualTo(newQuestion.getTitle());
		assertThat(actual.getContents()).isEqualTo(newQuestion.getContents());
	}
}
