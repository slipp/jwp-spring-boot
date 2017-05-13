package net.slipp.web;

import net.slipp.dto.QuestionDto;
import net.slipp.dto.QuestionsDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.RestAssuredIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiRAQuestionControllerTest extends RestAssuredIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(ApiRAQuestionControllerTest.class);

    @Test
    public void create() throws Exception {
        QuestionDto newQuestion = new QuestionDto(
                "TDD는 의미있는 활동인가?",
                "당근 엄청 의미있는 활동이고 말고.."
        );

        String location = createResource("/api/questions", newQuestion);

        QuestionDto actual = getResource(location, QuestionDto.class);
        log.debug("QuestionDTO : {}", actual);

        assertThat(actual.getTitle()).isEqualTo(newQuestion.getTitle());
        assertThat(actual.getContents()).isEqualTo(newQuestion.getContents());
    }

    @Test
    public void list_with_paging_params() throws Exception {
        createQuestions(5);

        QuestionsDto questions = getResources("/api/questions", 1, 5, QuestionsDto.class);
        log.debug("questions : {}", questions);

        assertThat(questions.size()).isEqualTo(5);
    }

    @Test
    public void list_without_paging_params() throws Exception {
        createQuestions(10);

        QuestionsDto questions = getResources("/api/questions", QuestionsDto.class);
        log.debug("questions : {}", questions);

        assertThat(questions.size()).isEqualTo(ApiQuestionController.DEFAULT_SIZE);
    }

    private void createQuestions(int size) {
        for (int i = 0; i < size; i++) {
            QuestionDto newQuestion = new QuestionDto(
                    i + ". TDD는 의미있는 활동인가?",
                    i + ".당근 엄청 의미있는 활동이고 말고.."
            );
            createResource("/api/questions", newQuestion);
        }
    }
}
