package net.slipp.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserValidationTest {
	private static final Logger log = LoggerFactory.getLogger(UserValidationTest.class);

	
	private static Validator validator;

	@BeforeClass
	public static void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void userIdWhenIsEmpty() throws Exception {
		User user = new User("", "password", "name", "javajigi@slipp.net");
		Set<ConstraintViolation<User>> constraintViolcations = validator.validate(user);
		assertThat(constraintViolcations.size(), is(1));
		
		for (ConstraintViolation<User> constraintViolation : constraintViolcations) {
			log.debug("violation error message : {}", constraintViolation.getMessage());
		}
	}
}
