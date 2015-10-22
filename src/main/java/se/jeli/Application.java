package se.jeli;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import se.jeli.config.AccessFilter;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	/**
	 * Enables this application to be compiled into a war file instead of a jar
	 * file and still be run with mvn spring-boot:run
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	@Bean
	protected FilterRegistrationBean myFilter(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new AccessFilter());
		List<String> urlPatterns = new ArrayList<>();
		urlPatterns.add("/result");
		urlPatterns.add("/confirmation");
		urlPatterns.add("/order");
		filterRegistrationBean.setUrlPatterns(urlPatterns);
		return filterRegistrationBean;
	}
	
}
