package se.jeli.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest hre = (HttpServletRequest) request;
		String method = hre.getMethod();

		StringBuffer requestURL = hre.getRequestURL();

		Enumeration<String> attributeNames2 = hre.getSession().getAttributeNames();
		while (attributeNames2.hasMoreElements()) {
			String string = (String) attributeNames2.nextElement();
			if (string.equals("loggedIn")) {

				if ((boolean) hre.getSession().getAttribute(string)) {
					if (method.equals("POST")) {
						chain.doFilter(request, response);
						return;
					}
				}
			}
		}

		System.out.println("method: " + method + " till sida " + requestURL + " INTE inloggad");
		hre.setAttribute("error", "Du är inte inloggad");
		//JESPER: nu kommer man till startsidan för din tomcat - inte till GoodProducts startsidan.
		((HttpServletResponse) response).sendRedirect("/error");

	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
