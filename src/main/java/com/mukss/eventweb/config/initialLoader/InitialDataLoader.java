package com.mukss.eventweb.config.initialLoader;

import java.time.LocalDateTime;
import java.util.Set;


import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mukss.eventweb.entities.Role;
import com.mukss.eventweb.entities.User;
import com.mukss.eventweb.services.RoleService;
import com.mukss.eventweb.services.UserService;
@Configuration
@Profile("default")
public class InitialDataLoader {
	
	private final static Logger log = LoggerFactory.getLogger(InitialDataLoader.class);
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
	
	@Autowired
	private UserService userService;
	
  @Autowired
	private RoleService roleService;

	
	@Bean
	CommandLineRunner initDatabase() {
		return args ->{
			// populate db with users
			if(userService.count() > 0) {
				log.info("Database is already populated with users.");
			}else {
				
				Role r1 = new Role();
				r1.setName("ADMIN");
				
				Role r2 = new Role();
				r2.setName("USER");
				
				Role r3 = new Role();
				r3.setName("MEMBER");
				
				roleService.save(r1);
				roleService.save(r2);
				roleService.save(r3);
				
				User user = new User();
				user.getRoles().add(r1);
				user.getRoles().add(r2);
				user.getRoles().add(r3);
				user.setFirstName("Mukss");
				user.setLastName("Mukss");
				user.setUserName("19990921");
				user.setPassword(this.passwordEncoder.encode("Mukss2024@"));
				user.setEmail("Info.mukss@gmail.com");
				user.setEnabled(true);
				user.setMembership("Confirmed");
				
				userService.save(user);
			}
			// populate db with categories
			// TODO: Implement categories and initialize here
			
			// populate db with posts
			
			
		};
	}
}