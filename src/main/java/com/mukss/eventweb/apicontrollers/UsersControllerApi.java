package com.mukss.eventweb.apicontrollers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import com.mukss.eventweb.assemblers.UserModelAssembler;
import com.mukss.eventweb.entities.User;
import com.mukss.eventweb.services.UserService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;

@RestController
@RequestMapping(value = "/api/users", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
public class UsersControllerApi {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserModelAssembler userAssembler;
	
	@GetMapping("/{id}")
	public EntityModel<User> getUserById(@PathVariable("id") long id){
		User user = userService.findById(id).orElseThrow();
		
		return userAssembler.toModel(user);
	}
	
	@GetMapping
	public CollectionModel<EntityModel<User>> getAllUsers(){
		ArrayList<User> allUsers = new ArrayList<User>();
		userService.findAll().forEach(allUsers::add);
		return userAssembler.toCollectionModel(allUsers)
		 .add(linkTo(methodOn(UsersControllerApi.class).getAllUsers()).withSelfRel());
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createGreeting(@RequestBody @Valid User user, BindingResult result) {

		if (result.hasErrors()) {
			return ResponseEntity.unprocessableEntity().build();
		}

		userService.save(user);
		
		EntityModel<User> entity = userAssembler.toModel(user);
		
		return ResponseEntity.created(entity.getRequiredLink(IanaLinkRelations.SELF).toUri()).build();
	}


}
