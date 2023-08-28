package com.mukss.eventweb.controllers;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mukss.eventweb.config.userdetails.CustomUserDetails;
import com.mukss.eventweb.entities.MembershipUser;
import com.mukss.eventweb.entities.Role;
import com.mukss.eventweb.entities.User;
import com.mukss.eventweb.services.MembershipService;
import com.mukss.eventweb.services.RoleService;
import com.mukss.eventweb.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
@RequestMapping(value = "/membership", produces = MediaType.TEXT_HTML_VALUE)
public class MembershipController {
	
	@Autowired
	private MembershipService membershipService;
	
	 @Autowired
	private RoleService roleService;
	 
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
	
	@GetMapping
	public String showRegistrationForm(Model model) {
		
		model.addAttribute("users", membershipService.findAll());
		
		
	    User user = new User();
	    model.addAttribute("user", user);
	    return "membership/new";
	}
	
	/* add new post via contents of form (form should pass Post object) */
	@PostMapping(value = "/new")
	public String createUser(@RequestBody @Valid @ModelAttribute MembershipUser membershipuser, BindingResult errors,
			Model model, RedirectAttributes redirectAttrs) {

		if (errors.hasErrors()) {
			model.addAttribute("user", membershipuser);
			return "membership/new";
		}
		// set author info and time info here
		
		//TODO: Handle if there is no user logged in.
		
		Role r = new Role();
		r.setName("USER");
		roleService.save(r);
		
		membershipuser.getRoles().add(r);
		membershipuser.setEnabled(true);
		
		String password = membershipuser.getPassword();
		membershipuser.setPassword(this.passwordEncoder.encode(password));
		
		membershipService.save(membershipuser);
		
		// save post after automatically adding relevant meta info
		
		redirectAttrs.addFlashAttribute("ok_message", "New registration added.");

		return "redirect:/sign-in";
	}	
	
	
}
