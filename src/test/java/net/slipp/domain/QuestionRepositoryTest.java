package net.slipp.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuestionRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	public void crud() throws Exception {
		User loginUser = userRepository.save(UserTest.TEST_USER);
		Question question = new Question("title", "contents");
		question.writeBy(loginUser);
		questionRepository.save(question);
	}
	
	@Test
	public void addAnswer() throws Exception {
		User loginUser = userRepository.save(UserTest.TEST_USER);
		Question question = new Question("title", "contents");
		question.writeBy(loginUser);
		Question savedQuestion = questionRepository.save(question);
		Answer answer = new Answer(loginUser, "answer contents");
		savedQuestion.addAnswer(answer);
		questionRepository.save(savedQuestion);
	}
}
