package support.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

public abstract class BasicAuthAcceptanceTest extends AcceptanceTest {
    protected TestRestTemplate basicAuthTemplate;

    @Autowired
    private UserRepository userRepository;

    protected User loginUser;

    @Before
    public void setup() {
        loginUser = userRepository.save(new User("sanjigi", "password", "name", "javajigi@slipp.net"));
        basicAuthTemplate = template.withBasicAuth(loginUser.getUserId(), loginUser.getPassword());
    }

    @After
    public void tearDown() {
        userRepository.delete(loginUser);
    }
}
