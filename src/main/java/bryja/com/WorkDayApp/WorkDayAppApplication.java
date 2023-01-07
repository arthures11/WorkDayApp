package bryja.com.WorkDayApp;

import bryja.com.WorkDayApp.Classes.User;
import bryja.com.WorkDayApp.Controllers.UserController;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@SpringBootApplication
@Configuration
@RestController
@EnableWebSecurity
public class WorkDayAppApplication extends WebSecurityConfigurerAdapter {

	@Value("/user/add")
	private String successUrl;
	@Value("/workdays")
	private String failureUrl;
	String[] staticResources = {
			"/css/**",
			"/assets/**",
			"/images/**",
			"/fonts/**",
			"/scripts/**",};

	public static void main(String[] args) {
		SpringApplication.run(WorkDayAppApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
				.csrf().disable()
				.authorizeRequests(a -> a
						.antMatchers("/", "/error", "/webjars/**", "/githubprivacyerror.html").permitAll()
						.antMatchers(staticResources).permitAll()
						.anyRequest().authenticated()
				)
				.exceptionHandling(e -> e
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				)
				.logout(l -> l
						.logoutSuccessUrl("/").permitAll()
				)
				.oauth2Login()
				.successHandler(successHandler())
				.failureHandler(failureHandler());


		// @formatter:on


	}

	@Bean
	SimpleUrlAuthenticationSuccessHandler successHandler() {
		return new SimpleUrlAuthenticationSuccessHandler(successUrl);
	}

	@Bean
	SimpleUrlAuthenticationFailureHandler failureHandler() {
		return new SimpleUrlAuthenticationFailureHandler(failureUrl);
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		// @formatter:off
		web
				.ignoring()
				.antMatchers("/h2-console/**");


		// @formatter:on

	}
	@GetMapping(value="/user", consumes = {"*/*"})
	public User user(@AuthenticationPrincipal OAuth2User principal) {
		String n = principal.getAttribute("name");
		String n2 = principal.getAttribute("email");
		String n3 = principal.getAttribute("password");
		User user = new User(n,n2,n3);
		return user;
	}
}

