package com.mukss.eventweb.assemblers;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.mukss.eventweb.apicontrollers.UsersControllerApi;
import com.mukss.eventweb.entities.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

	@Override
	public EntityModel<User> toModel(User user){
		return EntityModel.of(user,
				linkTo(methodOn(UsersControllerApi.class).getUserById(user.getId())).withSelfRel());
	}
}
