package com.satheesh.ticket.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satheesh.ticket.dao.UserRepository;
import com.satheesh.ticket.entities.TicketUser;
import com.satheesh.ticket.models.SimpleResponse;

@RestController
@RequestMapping("/user")
public class UserResource {

	@Autowired
	UserRepository userRepo;

	private Logger logger = LogManager.getLogger(this.getClass());

	@GetMapping
	public ResponseEntity<?> getUsers() {
		List<TicketUser> users = new ArrayList<>();
		try {
			userRepo.findAll().forEach(user -> users.add(user));
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEntity<>(new SimpleResponse(500, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

//	@GetMapping("/{id}/ticket")
//	public ResponseEntity<?> getTicketAssignedToUser(@PathVariable(value = "id") Integer userId) {
//		List<Ticket> tickets = new ArrayList<>();
//		try {
//			userRepo.findAssignedTickets(userId).forEach(ticket -> tickets.add(ticket));
//		} catch (Exception e) {
//			return new ResponseEntity<>(tickets, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return new ResponseEntity<>(tickets, HttpStatus.OK);
//	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody TicketUser user) {
		try {
			userRepo.save(user);
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEntity<>(new SimpleResponse(500, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new SimpleResponse(201, "User Created Successfully."), HttpStatus.CREATED);
	}

}
