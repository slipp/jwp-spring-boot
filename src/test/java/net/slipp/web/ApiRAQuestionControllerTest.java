package net.slipp.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import net.slipp.dto.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.RestAssuredIntegrationTest;

public class ApiRAQuestionControllerTest extends RestAssuredIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(ApiRAQuestionControllerTest.class);

    @Test
    public void create() throws Exception {
        QuestionDTO newQuestion = new QuestionDTO(
                "TDD는 의미있는 활동인가?",
                "당근 엄청 의미있는 활동이고 말고.."
        );

        String location = createResource("/api/questions", newQuestion);

        QuestionDTO actual = getResource(location, QuestionDTO.class);
        log.debug("QuestionDTO : {}", actual);

        assertThat(actual.getTitle()).isEqualTo(newQuestion.getTitle());
        assertThat(actual.getContents()).isEqualTo(newQuestion.getContents());
    }
}
