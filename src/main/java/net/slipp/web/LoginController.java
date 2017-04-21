package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import net.slipp.domain.User;
import net.slipp.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		try {
			User user = userService.login(userId, password);
			session.setAttribute("loginedUser", user);
			return "redirect:/";		
		} catch (IllegalStateException e) {
			return "/user/login_failed";
		}
	}
}
