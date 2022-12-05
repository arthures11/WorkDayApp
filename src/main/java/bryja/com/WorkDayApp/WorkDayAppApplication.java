package bryja.com.WorkDayApp;

import bryja.com.WorkDayApp.Classes.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
@Configuration
@EnableWebSecurity
public class WorkDayAppApplication extends WebSecurityConfigurerAdapter {
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
						.antMatchers("/", "/error", "/webjars/**").permitAll()
						.antMatchers(staticResources).permitAll()
						.anyRequest().authenticated()
				)
				.exceptionHandling(e -> e
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				)
				.logout(l -> l
						.logoutSuccessUrl("/").permitAll()
				)
				.oauth2Login();

		// @formatter:on

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
		User user = new User(n2,n);
		return user;
	}
}

