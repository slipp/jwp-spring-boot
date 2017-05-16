package net.slipp.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.slipp.dto.AnswerDto;
import net.slipp.dto.QuestionDto;
import net.slipp.dto.QuestionsDto;
import support.test.RestAssuredIntegrationTest;

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
    
    @Test
    public void delete_success() throws Exception {
        QuestionDto question = createQuestionWithAnswer(loginUser.getUserId());
        
        given_auth_json()
            .when()
            .delete(question.generateUrl())
            .then()
            .statusCode(204);            
    }

    @Test
    public void delete_failure() throws Exception {
        QuestionDto question = createQuestionWithAnswer("sanjigi");
        
        given_auth_json()
            .when()
            .delete(question.generateUrl())
            .then()
            .statusCode(400); 
    }
    
    private QuestionDto createQuestionWithAnswer(String userId) {
        QuestionDto newQuestion = new QuestionDto(
                "TDD는 의미있는 활동인가?",
                "당근 엄청 의미있는 활동이고 말고.."
        );
        QuestionDto question = getResource
                (createResource("/api/questions", newQuestion), QuestionDto.class);
        AnswerDto newAnswer = new AnswerDto("하지만 TDD는 너무 하기 힘들 활동임다.");
        createResource(question.generateUrl() + "/answers", newAnswer, userId);
        return question;
    }
}
