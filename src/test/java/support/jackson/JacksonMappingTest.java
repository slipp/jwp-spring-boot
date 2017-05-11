package support.jackson;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.slipp.domain.Answer;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionTest;
import net.slipp.domain.User;
import net.slipp.domain.UserTest;

public class JacksonMappingTest {
	private static final Logger log = LoggerFactory.getLogger(JacksonMappingTest.class);
	
	private ObjectMapper mapper;
	
	@Before
	public void setup() {
		mapper = new ObjectMapper();
	}
	
	@Test
	public void simple() throws Exception {
		Map<String, String> data = new HashMap<>();
		data.put("name", "jaesung");
		String result = mapper.writeValueAsString(data);
		log.debug("result : {}", result);
	}

	@Test
	public void question() throws Exception {
		User loginUser = UserTest.JAVAJIGI;
		Question question = QuestionTest.createByLoginUser(loginUser);
		String result = mapper.writeValueAsString(question);
		log.debug("result : {}", result);
	}
	
	@Test
	public void answer() throws Exception {
		User loginUser = UserTest.JAVAJIGI;
		Question question = QuestionTest.createByLoginUser(loginUser);
		Answer answer = new Answer(loginUser, "하지만 TDD는 너무 하기 힘들 활동임다.");
		String result = mapper.writeValueAsString(question.addAnswer(answer));
		log.debug("result : {}", result);
	}
}
