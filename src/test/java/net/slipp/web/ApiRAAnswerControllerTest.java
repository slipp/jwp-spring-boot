package net.slipp.web;

import org.junit.Test;

import net.slipp.dto.AnswerDto;
import net.slipp.dto.QuestionDto;
import support.test.RestAssuredIntegrationTest;

public class ApiRAAnswerControllerTest extends RestAssuredIntegrationTest {
    @Test
    public void create() {
        QuestionDto newQuestion = new QuestionDto(
                "TDD는 의미있는 활동인가?",
                "당근 엄청 의미있는 활동이고 말고.."
        );
        QuestionDto question = getResource
                (createResource("/api/questions", newQuestion), QuestionDto.class);
        AnswerDto newAnswer = new AnswerDto("하지만 TDD는 너무 하기 힘들 활동임다.");

        createResource(String.format("/api/questions/%d/answers", question.getId()), newAnswer);
    }
}
