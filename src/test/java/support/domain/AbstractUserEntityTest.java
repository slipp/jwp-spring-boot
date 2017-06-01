package support.domain;

import org.junit.Test;

import net.slipp.UnAuthorizedException;
import net.slipp.domain.UserTest;

public class AbstractUserEntityTest {
    private AbstractUserEntity userEntity = new AbstractUserEntity();

    @Test
    public void verifyAuthorizedOwner() {
        userEntity.writeBy(UserTest.JAVAJIGI);
        userEntity.verifyAuthorizedOwner(UserTest.JAVAJIGI);
    }
    
    @Test(expected = UnAuthorizedException.class)
    public void verifyAuthorized_not_owner() throws Exception {
        userEntity.writeBy(UserTest.JAVAJIGI);
        userEntity.verifyAuthorizedOwner(UserTest.SANJIGI);
    }
}