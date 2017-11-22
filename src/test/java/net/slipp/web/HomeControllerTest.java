package net.slipp.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import support.test.AbstractAcceptanceTest;

public class HomeControllerTest extends AbstractAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(HomeControllerTest.class);

    @Test
    public void home() {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

}
