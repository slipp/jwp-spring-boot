package net.slipp.web;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.security.LoginUser;
import net.slipp.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "userService")
	private UserService userService;
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}
	
	@PostMapping("")
	public String create(User user) {
		userService.add(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		List<User> users = userService.findAll();
		log.debug("user size : {}", users.size());
		model.addAttribute("users", users);
		return "/user/list";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
		model.addAttribute("user", userService.findById(id));
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser, @PathVariable long id, User target) {
		userService.update(loginUser, id, target);
		return "redirect:/users";
	}
	
}
