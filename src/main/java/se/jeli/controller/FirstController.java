package se.jeli.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import se.jeli.model.CreateUser;
import se.jeli.model.Validate;

/**
 * Controller for all subpages of this application. For logging in, out,
 * register new user, buying products. It is using Spring MVC with SpringBoot
 * and thymeleaf.
 * 
 * @author Lina
 * 
 */
@Controller
@SessionAttributes({ "userName", "loggedIn" })
public class FirstController {

	@Autowired
	private Validate validate;
	@Autowired
	private CreateUser createUser;

	/**
	 * Will return the user to homepage of the application
	 * 
	 * @return "index" for the homepage.
	 */
	@RequestMapping(value = "/")
	public String home() {

		return "index";
	}

	/**
	 * login Method is called when user tries to login to the application. If
	 * the user is authorized it returns "result" otherwise back to hompage
	 * "index".
	 * 
	 * @param name
	 *            - username form form in html page
	 * @param pw
	 *            - pw from form in html page
	 * @param model
	 * @param session
	 * @return "result" if the user is authorized it otherwise returns back to
	 *         hompage "index".
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String name, String pw, Model model, HttpSession session) {

		boolean authorized = validate.isAuthorized(name, pw);

		if (!authorized) {
			session.setAttribute("loggedIn", false);
			session.invalidate();
			model.addAttribute("error", "Fel inloggningsuppgifter.");
			return home();
		}

		session.setAttribute("loggedIn", true);
		model.addAttribute("userName", name);
		return "result";

	}

	/**
	 * Method for registration of new users.
	 * 
	 * @param name
	 *            from form in html page
	 * @param pw
	 *            from form in html page
	 * @param model
	 * @param session
	 * @return to homepage with result if the registration was successful or
	 *         not.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(String name, String pw, Model model, HttpSession session) {

		boolean success = createUser.createUsr(name, pw);

		if (!success) {
			session.setAttribute("loggedIn", false);
			session.invalidate();
			model.addAttribute("error", "Kombinationen av användarnamn och lösenord är ej tillåten.");
			return home();
		}

		model.addAttribute("success", "Din nya användare är registrerad, vänligen logga in med den.");
		return home();

	}

	/**
	 * Method when the user has entered an amount of products to buy.
	 * 
	 * @param number
	 *            from form in the html page
	 * @param model
	 * @param session
	 * @return "order" which is the page where epay is integrated
	 */
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public String buy(int number, Model model, HttpSession session) {
		model.addAttribute("number", number);
		return "order";
	}

	@RequestMapping(value = "/confirmation")
	public String confirmation(HttpSession httpSession, Model model) {

		// TODO: Här är det tänkt att vi ska hantera svar från epay
		return "confirmation";
	}

	/**
	 * Method to log the user out.
	 * 
	 * @param model
	 * @param session
	 * @return "index" homepage
	 */
	@RequestMapping(value = "/logout")
	public String logout(Model model, HttpSession session) {
		session.setAttribute("loggedIn", false);
		session.invalidate();
		return home();
	}

	//OKLART OM NEDAN ANVÄNDS BORTKOMMENTERAT SÅ LÄNGE
//	/**
//	 * Method that wi
//	 * 
//	 * @param session
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/result", method = RequestMethod.POST)
//	public String result(HttpSession session, Model model) {
//
//		return "result";
//	}
}
