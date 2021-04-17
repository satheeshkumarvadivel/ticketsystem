package com.satheesh.ticket.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.satheesh.ticket.entities.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer>, JpaSpecificationExecutor<Ticket> {

	@Query(value = "select tic.assignedto, count(u.name) from Ticket tic join Ticketuser u on tic.assignedto = u.id group by tic.assignedto order by count limit 1", nativeQuery = true)
	public int getTicketUserWithLeastTicketCount();

	@Transactional
	@Modifying
	@Query(value = "update Ticket set status = 'CLOSED' WHERE status = 'RESOLVED' AND createdtime <= :currentTime", nativeQuery = true)
	public void updateResolvedTicketsToClosed(@Param("currentTime") long currentTime);

}
