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
import com.mukss.eventweb.entities.Attend;
import com.mukss.eventweb.entities.Event;
import com.mukss.eventweb.entities.User;
import com.mukss.eventweb.exceptions.EventNotFoundException;
import com.mukss.eventweb.services.AttendService;
import com.mukss.eventweb.services.EventService;

@Controller
@RequestMapping(value = "/attends", produces = MediaType.TEXT_HTML_VALUE)
public class AttendsController {

	@Autowired
	private EventService eventService;

	@Autowired
	private AttendService attendService;

	// Adding new attend
	@GetMapping("/new")
	public String newAttend(Model model) {
		// if model does not have attend, initialize a new attend
		if (!model.containsAttribute("attend")) {
			model.addAttribute("attend", new Attend());
		}
		return "attends/new";
	}

	/*
	 * add new attend with comment via contents of form (form should pass ? object)
	 */
	@PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createAttend(@RequestBody @Valid @ModelAttribute Attend attend, BindingResult errors,
			@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {

		// return to original url if error
		if (errors.hasErrors()) {
			model.addAttribute("attend", attend);
			return "/events/" + id;
		}
		// set author info and time info here

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		if (principal instanceof CustomUserDetails) {
			user = ((CustomUserDetails) principal).getUser();
		}
		// get post from id given in url
		Event event = eventService.findById(id).orElseThrow(() -> new EventNotFoundException(id));

		// TODO: Handle if there is no user logged in.
		if (!model.containsAttribute("attend")) {
			model.addAttribute("attend", new Attend());
		}
		attend.setUser(user);
		attend.setTimeUploaded(LocalDateTime.now());
		attend.setLastEdited(LocalDateTime.now());
		attend.setEvent(event);
		attendService.save(attend);
		redirectAttrs.addFlashAttribute("ok_message", "New attend added.");

		// return to original url upon saving
		return "redirect:/events/" + id;
	}

	// delete one attend
	@DeleteMapping("/{id}")
	public String deleteAttend(@PathVariable("id") long id, RedirectAttributes redirectAttrs) {
		attendService.deleteById(id);
		redirectAttrs.addFlashAttribute("ok_message", "Attend deleted.");
		return "redirect:/events";
	}
}