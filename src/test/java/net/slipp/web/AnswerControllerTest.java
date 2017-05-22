package net.slipp.web;

import static net.slipp.domain.QuestionTest.createByLoginUser;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class AnswerControllerTest extends BasicAuthAcceptanceTest {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	public void addAnswer() throws Exception {
		Question savedQuestion = questionRepository.save(createByLoginUser(loginUser));
		
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				.urlEncodedForm()
				.addParameter("contents", "당근 엄청 의미있는 활동이고 말고..")
				.build();

		ResponseEntity<String> response = basicAuthTemplate.postForEntity(savedQuestion.generateUrl() + "/answers", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
	}
}
