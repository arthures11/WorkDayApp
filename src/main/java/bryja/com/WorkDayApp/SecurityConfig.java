package bryja.com.WorkDayApp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SecurityConfig {

    @Value("/user/add")
    private String successUrl;
    @Value("/workdays")
    private String failureUrl;

    @Bean
    protected SecurityFilterChain chains(HttpSecurity http) throws Exception {

        http

                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/swagger-ui/**", "/swagger-ui/#/", "/swagger-ui**").hasRole("USER")
                        .requestMatchers("/h2-console/**", "/h2-console/#/", "/h2-console**").hasRole("USER")
                        .requestMatchers("/index.html", "/error", "/webjars/**", "/githubprivacyerror.html","/css/**","/assets/**", "/images/**", "/fonts/**", "/scripts/**").permitAll()
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

        return http.build();

    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler(successUrl);
    }

    @Bean
    SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler(failureUrl);
    }

}