package net.slipp.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import net.slipp.UnAuthorizedException;

public class UserTest {
    private User newUser(String userId) {
        return newUser(userId, "password");
    }

    private User newUser(String userId, String password) {
        return new User(userId, password, "name", "javajigi@slipp.net");
    }

    @Test
    public void update_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = origin;
        User target = new User("sanjigi", "password", "name2", "javajigi@slipp.net2");
        origin.update(loginUser, target);
        assertThat(origin, is(target));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = newUser("javajigi");
        User target = new User("sanjigi", "password", "name2", "javajigi@slipp.net2");
        origin.update(loginUser, target);
    }

    @Test
    public void update_match_password() {
        User origin = newUser("sanjigi");
        User target = new User("sanjigi", "password", "name2", "javajigi@slipp.net2");
        origin.update(origin, target);
        assertThat(origin, is(target));
    }

    @Test
    public void update_mismatch_password() {
        User origin = newUser("sanjigi", "password");
        User target = newUser("sanjigi", "password2");
        origin.update(origin, target);
        assertFalse(origin.equals(target));
    }
}
