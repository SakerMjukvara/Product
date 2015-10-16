package se.jeli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.jeli.model.Validate;

@Controller
public class FirstController {

	private Validate validate;

	@Autowired
	public FirstController(Validate validate) {
		this.validate = validate;

	}

	@RequestMapping(value="/login", method= RequestMethod.POST)
	public String login(String name, String pw, Model model) {

		boolean authorized = validate.isAuthorized(name, pw);
		
		model.addAttribute("loginResult", authorized);

		return "result";
	}

	@RequestMapping("/index")
	public String home() {
		return "login";
	}

}
