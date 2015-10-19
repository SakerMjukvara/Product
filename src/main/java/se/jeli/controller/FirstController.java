package se.jeli.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String name, String pw, Model model) {

		boolean authorized = validate.isAuthorized(name, pw);

		if (authorized) {
			model.addAttribute("userName", name);
			return "result";
		}

		model.addAttribute("loginStatus", "Login misslyckades");
		return home();

	}

	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public String buy(int number, Model model, HttpServletRequest httpSession) {
		Enumeration<String> attributeNames = httpSession.getAttributeNames();
		Map<String, Object> attributes = new HashMap<>();
		while (attributeNames.hasMoreElements()) {

			String nextElement = attributeNames.nextElement();
			Object attribute = httpSession.getAttribute(nextElement);
			attributes.put(nextElement, attribute);
		}

		model.addAttribute("attributes", attributes);
		model.addAttribute("number", number);

		return "order";
	}

	@RequestMapping(value = "confirmation")
	public String confirmation(HttpSession httpSession){
		
		
		return "confirmation";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		return home();
	}

	@RequestMapping("/")
	public String home() {
		return "index";
	}

}
