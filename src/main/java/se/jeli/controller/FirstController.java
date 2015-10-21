package se.jeli.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import se.jeli.model.Validate;

/**
 * Controller for logging and redirecting
 * 
 * @author Lina
 *
 */
@Controller
@SessionAttributes({ "userName", "loggedIn" })
public class FirstController {

	private Validate validate;

	@Autowired
	public FirstController(Validate validate) {
		this.validate = validate;

	}

	/**
	 * 
	 * @param name
	 *            - userName
	 * @param pw
	 *            - password
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String name, String pw, Model model) {

		boolean authorized = validate.isAuthorized(name, pw);

		if (!authorized) {
			model.addAttribute("loggedIn", "false");
			model.addAttribute("error", "Fel inloggningsuppgifter.");
			return home();
		}

		model.addAttribute("userName", name);
		model.addAttribute("loggedIn", "true");
		return "result";

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(String name, String pw, Model model) {

		boolean success = validate.validUser(name, pw);

		if (!success) {
			model.addAttribute("loggedIn", "false");
			model.addAttribute("error", "Kombinationen av användarnamn och lösenord är ej tillåten.");
			return home();
		}

		model.addAttribute("success", "Din nya användare är registrerad, vänligen logga in med den.");
		return home();

	}

	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public String buy(int number, Model model, HttpSession httpSession) {
		if (((String) httpSession.getAttribute("loggedIn")).equals("false")) {
			model.addAttribute("error", "Du har inte loggat in.");
			return home();
		}
		model.addAttribute("number", number);
		return "order";
	}

	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public String buy(Model model, HttpSession httpSession) {
		if (((String) httpSession.getAttribute("loggedIn")).equals("false")) {
			model.addAttribute("error", "Du har inte loggat in.");
			return home();
		}
		return "result";
	}

	@RequestMapping(value = "/confirmation")
	public String confirmation(HttpSession httpSession, Model model) {
		if (((String) httpSession.getAttribute("loggedIn")).equals("false")) {
			model.addAttribute("error", "Du har inte loggat in.");
			return home();
		}
		return "confirmation";
	}

	@RequestMapping(value = "/logout")
	public String logout(Model model) {
		model.addAttribute("loggedIn", "false");
		return home();
	}

	@RequestMapping(value = "/result")
	public String result(HttpSession httpSession, Model model) {

		if (((String) httpSession.getAttribute("loggedIn")).equals("false")) {
			model.addAttribute("error", "Du har inte loggat in.");
			return home();
		}
		return "result";
	}

	@RequestMapping(value = "/")
	public String home() {
		validate.extracted();
		return "index";
	}
}
