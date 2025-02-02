package com.mukss.eventweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.mukss.eventweb.config.userdetails.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

//	public static final String ADMIN_ROLE = "ADMINISTRATOR";

	// List the mappings/methods for which no authorisation is required.
	private static final RequestMatcher[] NO_AUTH = { new AntPathRequestMatcher("/webjars/**", "GET"),
			new AntPathRequestMatcher("/", "GET"),
			new AntPathRequestMatcher("/main", "GET"),
			new AntPathRequestMatcher("/events", "GET"),
			new AntPathRequestMatcher("/events/{id}", "GET"),
			new AntPathRequestMatcher("/register", "GET"),
			new AntPathRequestMatcher("/images/**", "GET"),
			new AntPathRequestMatcher("/authenticate", "POST"),
			new AntPathRequestMatcher("/register/new", "POST"),
	};
	
	private static final RequestMatcher[] USER_AUTH = {
			new AntPathRequestMatcher("/events/{id}", "GET"),
			new AntPathRequestMatcher("/membership", "GET"),
			new AntPathRequestMatcher("/membership", "POST"),
			new AntPathRequestMatcher("/attends", "POST"),
			
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// By default, all requests are authenticated except our specific list.
		http.authorizeRequests()
		.requestMatchers(NO_AUTH).permitAll()
		.requestMatchers(USER_AUTH).hasAnyAuthority("USER", "MEMBER")
		.anyRequest().hasAuthority("ADMIN");

		// Use form login/logout for the Web.
		http.formLogin().loginPage("/sign-in").permitAll();
		http.logout().logoutUrl("/sign-out").logoutSuccessUrl("/").permitAll();
		

		// Use HTTP basic for the API.
		http.requestMatcher(new AntPathRequestMatcher("/api/**")).httpBasic();

		// Only use CSRF for Web requests.
		http.antMatcher("/**").csrf().ignoringAntMatchers("/api/**");
	}

}