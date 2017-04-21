package net.slipp.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void update_match_password() {
		User origin = new User("sanjigi", "password", "name", "javajigi@slipp.net");
		User target = new User("sanjigi", "password", "name2", "javajigi@slipp.net2");
		origin.update(target);
		assertThat(origin, is(target));
	}
	
	@Test
	public void update_mismatch_password() {
		User origin = new User("sanjigi", "password", "name", "javajigi@slipp.net");
		User target = new User("sanjigi", "password2", "name2", "javajigi@slipp.net2");
		origin.update(target);
		assertFalse(origin.equals(target));
	}
}
