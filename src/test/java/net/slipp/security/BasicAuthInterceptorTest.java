package net.slipp.security;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import net.slipp.domain.User;
import net.slipp.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class BasicAuthInterceptorTest {
    @Mock
    private UserService userService;
    
    @InjectMocks
    private BasicAuthInterceptor basicAuthInterceptor;
    
    @Test
    public void preHandle_로그인_성공() throws Exception {
        String userId = "userId";
        String password = "password";
        MockHttpServletRequest request = basicAuthHttpRequest(userId, password);
        User loginUser = new User(userId, "password", "name", "javajigi@slipp.net");
        when(userService.login(userId, password)).thenReturn(loginUser);
        
        basicAuthInterceptor.preHandle(request, null, null);
        assertThat(request.getSession().getAttribute(HttpSessionUtils.USER_SESSION_KEY), is(loginUser));
    }

    private MockHttpServletRequest basicAuthHttpRequest(String userId, String password) {
        String encodedBasicAuth = Base64.getEncoder().encodeToString(String.format("%s:%s", userId, password).getBytes());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic " + encodedBasicAuth);
        return request;
    }
}
