package net.slipp.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import net.slipp.UnAuthorizedException;

public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    public static User newUser(String userId) {
        return newUser(userId, "password");
    }

    public static User newUser(String userId, String password) {
        return new User(1L, userId, password, "name", "javajigi@slipp.net");
    }

    @Test
    public void update_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = origin;
        User target = new User("sanjigi", "password", "name2", "javajigi@slipp.net2");
        origin.update(loginUser, target);
        assertThat(origin.getName(), is(target.getName()));
        assertThat(origin.getEmail(), is(target.getEmail()));
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
        assertThat(origin.getName(), is(target.getName()));
        assertThat(origin.getEmail(), is(target.getEmail()));
    }

    @Test
    public void update_mismatch_password() {
        User origin = newUser("sanjigi", "password");
        User target = new User("sanjigi", "password2", "name2", "javajigi@slipp.net2");
        origin.update(origin, target);
        assertThat(origin.getName(), is(not(target.getName())));
        assertThat(origin.getEmail(), is(not(target.getEmail())));
    }
}
