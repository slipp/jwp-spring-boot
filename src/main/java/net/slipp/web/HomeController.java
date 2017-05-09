package net.slipp.web;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.slipp.domain.QuestionRepository;
import net.slipp.service.QnaService;

@Controller
public class HomeController {
	@Resource(name = "qnaService")
	private QnaService qnaService;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("questions", qnaService.findAll());
		return "home";
	}
}
