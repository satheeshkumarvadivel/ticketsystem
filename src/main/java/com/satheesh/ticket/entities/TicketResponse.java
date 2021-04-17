package com.satheesh.ticket.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "response")
@Table(name = "response")
public class TicketResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "ticketid")
	Integer ticketid;

	@OneToOne
	@JoinColumn(name = "respondedby", referencedColumnName = "id")
	TicketUser respondedBy;

	@Column(name = "response")
	String response;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTicketid() {
		return ticketid;
	}

	public void setTicketid(Integer ticketid) {
		this.ticketid = ticketid;
	}

	public TicketUser getRespondedBy() {
		return respondedBy;
	}

	public void setRespondedBy(TicketUser respondedBy) {
		this.respondedBy = respondedBy;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
