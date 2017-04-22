package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.slipp.UnAuthenticationException;
import net.slipp.domain.User;
import net.slipp.security.HttpSessionUtils;
import net.slipp.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String form() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		try {
			User user = userService.login(userId, password);
			session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
			return "redirect:/";		
		} catch (UnAuthenticationException e) {
			return "/user/login_failed";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
}
