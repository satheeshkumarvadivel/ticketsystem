package com.satheesh.ticket.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satheesh.ticket.entities.Ticket;
import com.satheesh.ticket.entities.TicketResponse;
import com.satheesh.ticket.models.SimpleResponse;
import com.satheesh.ticket.services.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketResource {

	@Autowired
	TicketService ticketService;

	private Logger logger = LogManager.getLogger(this.getClass());

	@GetMapping
	public ResponseEntity<?> getTickets(@RequestParam(value = "agent", required = false) String agent,
			@RequestParam(value = "customer", required = false) String customer,
			@RequestParam(value = "status", required = false) String status) {
		List<Ticket> tickets = new ArrayList<>();
		try {
			tickets = ticketService.getAllTickets(agent, customer, status);
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEntity<>(new SimpleResponse(500, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTicketById(@PathVariable(value = "id") Integer ticketId) {
		Ticket ticket = null;
		try {
			ticket = ticketService.getTicketById(ticketId);
		} catch (NoSuchElementException noElem) {
			return new ResponseEntity<>(new SimpleResponse(404, "Ticket Not Found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEntity<>(new SimpleResponse(500, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> createTicket(@Valid @RequestBody Ticket ticket) {
		Ticket savedTicket;
		try {
			savedTicket = ticketService.createTicket(ticket);
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEntity<>(new SimpleResponse(500, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(
				new SimpleResponse(201, "Ticket Created Successfully with id " + savedTicket.getId()),
				HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateTicket(@PathVariable(value = "id") Integer ticketId,
			@Valid @RequestBody Ticket ticket) {
		try {
			ticket.setId(ticketId);
			ticketService.updateTicket(ticket);
		} catch (NoSuchElementException noElem) {
			return new ResponseEntity<>(new SimpleResponse(404, "Ticket Not Found"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEntity<>(new SimpleResponse(500, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new SimpleResponse("Ticket updated Successfully."), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTicketById(@PathVariable(value = "id") Integer ticketId) {
		try {
			ticketService.deleteTicketById(ticketId);
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEntity<>(new SimpleResponse(500, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new SimpleResponse("Ticket Deleted Successfully."), HttpStatus.OK);
	}

	@PostMapping("/{id}/response")
	public ResponseEntity<?> createTicketResponse(@PathVariable(value = "id") Integer ticketId,
			@RequestBody TicketResponse ticket) {
		try {
			ticket.setTicketid(ticketId);
			ticketService.createTicketResponse(ticket);
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEntity<>(new SimpleResponse(500, "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new SimpleResponse(201, "Response Created Successfully."), HttpStatus.CREATED);
	}

}
