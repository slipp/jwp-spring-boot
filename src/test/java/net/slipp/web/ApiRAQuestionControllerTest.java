package net.slipp.web;

import static org.assertj.core.api.Assertions.assertThat;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.dto.QuestionsDTO;
import org.junit.Test;

import net.slipp.dto.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import support.test.RestAssuredIntegrationTest;

import javax.annotation.Resource;

public class ApiRAQuestionControllerTest extends RestAssuredIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(ApiRAQuestionControllerTest.class);

    @Resource(name = "questionRepository")
    private QuestionRepository questionRepository;

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

    @Test
    public void list_with_paging_params() throws Exception {
        createQuestions(11);

        QuestionsDTO questions = getResources("/api/questions", 1, 5, QuestionsDTO.class);
        log.debug("questions : {}", questions);

        assertThat(questions.size()).isEqualTo(5);
    }

    @Test
    public void list_without_paging_params() throws Exception {
        createQuestions(11);

        QuestionsDTO questions = getResources("/api/questions", QuestionsDTO.class);
        log.debug("questions : {}", questions);

        assertThat(questions.size()).isEqualTo(ApiQuestionController.DEFAULT_SIZE);
    }

    private void createQuestions(int size) {
        for (int i = 0; i < size; i++) {
            Question question = new Question(
                    i + ". TDD는 의미있는 활동인가?",
                    i + ".당근 엄청 의미있는 활동이고 말고..");
            question.writeBy(loginUser);
            questionRepository.save(question);
        }
    }
}
