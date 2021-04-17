package com.satheesh.ticket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.satheesh.ticket.entities.TicketUser;

@Repository
public interface UserRepository extends CrudRepository<TicketUser, Integer> {

//	@Query("SELECT t from Ticket t where assignedto = ?1")
//	public List<Ticket> findAssignedTickets(int userid);

}
