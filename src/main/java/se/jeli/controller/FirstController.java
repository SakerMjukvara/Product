package se.jeli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import se.jeli.model.Validate;

@Controller
public class FirstController {

	@RequestMapping("/login")
	public String login(String name, String pw, Model model){

		Validate validate = new Validate();
		
		boolean authorized = validate.isAuthorized(name, pw);
		model.addAttribute("loginResult",authorized);
		
		return "result";
	}
	
}
