package com.satheesh.ticket.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.satheesh.ticket.entities.Ticket;
import com.satheesh.ticket.entities.TicketResponse;

@Service
public interface TicketService {
	public List<Ticket> getAllTickets(String agent, String customer, String status);

	public Ticket getTicketById(int ticketId);

	public Ticket createTicket(Ticket ticket);

	public TicketResponse createTicketResponse(TicketResponse ticketResponse);

	public void updateTicket(Ticket ticket);

	public void deleteTicketById(int ticketId);
}
