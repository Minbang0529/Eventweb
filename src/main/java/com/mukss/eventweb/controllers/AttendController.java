package com.mukss.eventweb.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mukss.eventweb.config.userdetails.CustomUserDetails;
import com.mukss.eventweb.entities.Attend;
import com.mukss.eventweb.entities.AttendsDTO;
import com.mukss.eventweb.entities.Event;
import com.mukss.eventweb.entities.User;
import com.mukss.eventweb.exceptions.EventNotFoundException;
import com.mukss.eventweb.services.AttendService;
import com.mukss.eventweb.services.EventService;

@Controller
@RequestMapping(value = "/attends", produces = MediaType.TEXT_HTML_VALUE)
public class AttendController {

    @Autowired
    private EventService eventService;

    @Autowired
    private AttendService attendService;
    
    @GetMapping("/{id}/confirm")
    public String setConfirm(@PathVariable("id") long id, 
    		@RequestParam(value = "eventId", required = true) String eventId,
			Model model, RedirectAttributes redirectAttrs) {
    	
		Attend attend = attendService.findById(id).get();
		attend.setStatus("Confirmed");
		attendService.save(attend);
		
		return "redirect:/events/" + eventId;
	}

    /*
     * add new attend with comment via contents of form (form should pass ? object)
     */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createAttend(@RequestBody @Valid @ModelAttribute Attend eattend, BindingResult errors,
    		@RequestParam(value = "eventId", required = true) String eventId, 
    		@RequestParam(value = "status", defaultValue = "Waiting") String status,
    		Model model, RedirectAttributes redirectAttrs) {
    	
    	long parsedEventId = Long.parseLong(eventId);
    	
        // return to original url if error
        if (errors.hasErrors()) {
        	Event event = eventService.findById(parsedEventId).orElseThrow(() -> new EventNotFoundException(parsedEventId));
    		
        	String imageString = Base64.getEncoder().encodeToString(event.getData());

    		// attend 추가		
    		model.addAttribute("event", event);
    		model.addAttribute("eventImage", imageString);
    		model.addAttribute("imageFileType", event.getImageFileType());
            
    		model.addAttribute("eattend", eattend);
            
    		return "redirect:/events/" + eventId;
        }

        // set author info and time info here
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if (principal instanceof CustomUserDetails) {
            user = ((CustomUserDetails) principal).getUser();
        }
        
        // get post from id given in url
        Event event = eventService.findById(parsedEventId).orElseThrow(() -> new EventNotFoundException(parsedEventId));

        // TODO: Handle if there is no user logged in.
        if (!model.containsAttribute("eattend")) {
            model.addAttribute("eattend", new Attend());
        }

        eattend.setUser(user);
        eattend.setTimeUploaded(LocalDateTime.now());
        eattend.setLastEdited(LocalDateTime.now());
        eattend.setEvent(event);
        eattend.setStatus(status);
        attendService.save(eattend);
        redirectAttrs.addFlashAttribute("ok_message", "New attend added.");

        // return to original url upon saving
        return "redirect:/events/" + eventId;
    }
    
    @PostMapping(value = "/editAttends", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editAttends(@RequestBody @Valid  @ModelAttribute AttendsDTO attendsForm, BindingResult errors,
    		@RequestParam(value = "eventId", required = true) String eventId, RedirectAttributes redirectAttrs) { 
    	for(Attend a: attendsForm.getAttendList()) {
    		a.setTimeUploaded(LocalDateTime.now());
    		attendService.save(a);
    	}
    	
    	redirectAttrs.addFlashAttribute("ok_message", "Attendances Saved");
    	return "redirect:/events/" + eventId;
    }

    // delete one attend
    @DeleteMapping("/{id}")
    public String deleteAttend(@PathVariable("id") long id, RedirectAttributes redirectAttrs) {
        attendService.deleteById(id);
        redirectAttrs.addFlashAttribute("ok_message", "Attend deleted.");
        return "redirect:/events";
    }
}