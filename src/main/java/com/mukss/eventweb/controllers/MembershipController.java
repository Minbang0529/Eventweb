package com.mukss.eventweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mukss.eventweb.config.userdetails.CustomUserDetails;
import com.mukss.eventweb.entities.Attend;
import com.mukss.eventweb.entities.Membership;
import com.mukss.eventweb.entities.MembershipsDTO;
import com.mukss.eventweb.entities.Role;
import com.mukss.eventweb.entities.User;
import com.mukss.eventweb.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mukss.eventweb.services.RoleService;
import com.mukss.eventweb.services.UserService;


@Controller
@RequestMapping(value = "/membership", produces = MediaType.TEXT_HTML_VALUE)
public class MembershipController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public String showRegistrationForm(Model model, @RequestParam(value = "userName", required = true) String username) {
		
		// Whether the person is logged in or not, or the username matches
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		if (principal instanceof CustomUserDetails) {
			user = ((CustomUserDetails)principal).getUser();
		}
		
		
		if (user == null || !user.getUserName().equals(username)) {
			return "redirect:/";
		}
		
		Membership membershipRegistrationEntity = new Membership();
		User userRetrieved = userService.findByName(username).orElseThrow(()-> new UserNotFoundException(username));
		
		membershipRegistrationEntity.setUserName(userRetrieved.getUserName());
		membershipRegistrationEntity.setFirstName(userRetrieved.getFirstName());
		membershipRegistrationEntity.setLastName(userRetrieved.getLastName());
		membershipRegistrationEntity.setMembershipCheckbox(userRetrieved.getMembership().equals("Waiting") ? true : false);

	    model.addAttribute("membershipRegistrationEntity", membershipRegistrationEntity);
	    return "membership/new";
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createMembership(@RequestBody @Valid @ModelAttribute Membership membershipRegistrationEntity,@RequestParam(value = "status", defaultValue = "Waiting") String status, BindingResult errors, Model model, RedirectAttributes redirectAttrs) {
		
	User user = userService.findByName(membershipRegistrationEntity.getUserName()).orElseThrow(()-> new UserNotFoundException(membershipRegistrationEntity.getUserName()));
	
	if (membershipRegistrationEntity.isMembershipCheckbox()) {
		user.setMembership("Waiting");
		userService.save(user);
	}
	
	return "redirect:/events";
	}
	
	@GetMapping("/index")
	public String getMemberships(Model model) {
		List<User> waitingMembers = userService.findBymembership("Waiting");

		MembershipsDTO membershipsDTO = new MembershipsDTO();
		membershipsDTO.setUsersList(waitingMembers);

		model.addAttribute("membershipsDTO", membershipsDTO);
		return "membership/index";
	}
	
	@GetMapping("/list")
	public String getMemberlist(Model model) {
		List<User> confirmedMembers = userService.findBymembership("Confirmed");

		Iterable<User> ulist = userService.findAll();
		List<User> memlist = new ArrayList<User>();
		for (User u : ulist) {
			if (u.getMembership().contains("firm")) {
				memlist.add(u);
			}
		}
		
		MembershipsDTO membershipsDTO = new MembershipsDTO();
		membershipsDTO.setUsersList(memlist);

		model.addAttribute("membershipsDTO", membershipsDTO);
		return "membership/index";
	}
	
	@PostMapping(value="/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String updateMemberships(Model model, @RequestBody @Valid @ModelAttribute MembershipsDTO membershipsDTO, RedirectAttributes redirectAttrs) {
		Role memberRole = roleService.findByname("MEMBER").get();
		if(memberRole == null) {
			memberRole = new Role();
			memberRole.setName("MEMBER");
			roleService.save(memberRole);
		}
		Role userRole = roleService.findByname("USER").get();
		if(userRole == null) {
			userRole = new Role();
			userRole.setName("USER");
			roleService.save(userRole);
		}

		for (User u: membershipsDTO.getUsersList()) {
			Set<Role> roles = new HashSet<>();
			switch(u.getMembership()) {
				case "Confirmed":
					roles.add(memberRole);
					u.setRoles(roles);
					break;
				case "Rejected":
					roles.add(userRole);
					u.setRoles(roles);
					break;
			}
			userService.save(u);
		}	

		return "redirect:/membership/index";
	}
}
