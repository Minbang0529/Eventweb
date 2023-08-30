package com.mukss.eventweb.controllers;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mukss.eventweb.config.userdetails.CustomUserDetails;
import com.mukss.eventweb.entities.Membership;
import com.mukss.eventweb.entities.User;
import com.mukss.eventweb.services.MembershipService;

@Controller
@RequestMapping(value = "/membership", produces = MediaType.TEXT_HTML_VALUE)
public class MembershipController {


    @Autowired
    private MembershipService membershipService;

	
	@GetMapping
	public String showRegistrationForm(Model model) {
		
		model.addAttribute("users", membershipService.findAll());
		
		
	    User user = new User();
	    model.addAttribute("user", user);
	    return "membership/new";
	}
	
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createMembership(@RequestBody @Valid @ModelAttribute Membership membership, BindingResult errors, 
    		Model model, RedirectAttributes redirectAttrs) {
    	
    	
        // return to original url if error
        if (errors.hasErrors()) {
            model.addAttribute("membership", membership);
            return "membership/new";
        }

        // set author info and time info here
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if (principal instanceof CustomUserDetails) {
            user = ((CustomUserDetails) principal).getUser();
        }
        

        // TODO: Handle if there is no user logged in.
        if (!model.containsAttribute("membership")) {
            model.addAttribute("membership", new Membership());
        }

        membership.setUser(user);
        membership.setTimeUploaded(LocalDateTime.now());
        membership.setLastEdited(LocalDateTime.now());
        membershipService.save(membership);
        redirectAttrs.addFlashAttribute("ok_message", "New membership added.");

        // return to original url upon saving
        return "redirect:/sign-in";
    }

    // delete one attend
    @DeleteMapping("/{id}")
    public String deleteMembership(@PathVariable("id") long id, RedirectAttributes redirectAttrs) {
        membershipService.deleteById(id);
        redirectAttrs.addFlashAttribute("ok_message", "Membership deleted.");
        return "redirect:/sign-in";
    }
}
