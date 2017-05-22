package net.slipp.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;

import net.slipp.domain.DeleteHistoryRepository;
import net.slipp.domain.QuestionRepository;
import net.slipp.dto.AnswerDto;
import net.slipp.dto.QuestionDto;
import support.test.RestAssuredAcceptanceTest;

public class TransactionTest extends RestAssuredAcceptanceTest {
    @Resource(name = "questionRepository")
    private QuestionRepository questionRepository;
    
    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;

    
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
    
    @Test
    public void delete_transaction() throws Exception {
        QuestionDto question = createQuestionWithAnswer(loginUser.getUserId());
        
        given_auth_json()
            .when()
            .delete(question.generateUrl())
            .then()
            .statusCode(204);
        
        assertThat(deleteHistoryRepository.count(), is(2L));
        assertThat(questionRepository.findOne(question.getId()).isDeleted(), is(true));
    }
}
