package com.mukss.eventweb.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mukss.eventweb.entities.User;
import com.mukss.eventweb.repositories.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/authenticate")
    public String processLogin(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password
    ) {
        // Query the database for the user with the provided email and password
        Optional<User> user = userRepository.findByuserName(userName);
        
        if (user.isPresent()) {
            // Successful login, redirect to a success page
        	if (password.equals(user.get().getPassword())) {
        		return "redirect:/main.html";
        	} else {
        		return "redirect:/login.html?error=2";
        	}
        } else {
            // Failed login, redirect back to login page with an error message
            return "redirect:/login.html?error=1";
        }
    }
}