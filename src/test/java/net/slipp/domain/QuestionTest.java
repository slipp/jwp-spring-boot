package net.slipp.domain;

public class QuestionTest {
    public static Question createByLoginUser(User loginUser) {
        Question question = new Question("TDD는 의미있는 활동인가?", "당근 엄청 의미있는 활동이고 말고..");
        question.writeBy(loginUser);
        return question;
    }
}
