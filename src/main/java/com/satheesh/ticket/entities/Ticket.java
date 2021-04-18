package com.satheesh.ticket.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.satheesh.ticket.models.TicketPriority;
import com.satheesh.ticket.models.TicketStatus;
import com.satheesh.ticket.models.TicketType;

@Entity(name = "ticket")
@Table(name = "ticket")
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	protected Ticket() {
	}

	@NotBlank(message = "Please provide title")
	@Size(max = 240, message = "Title cannot be longer than 240 characters")
	String title;

	@Size(max = 1000, message = "Description cannot be longer than 1000 characters")
	String description;

	@Enumerated(EnumType.STRING)
	TicketType type = TicketType.BUG;

	@Enumerated(EnumType.STRING)
	TicketPriority priority = TicketPriority.P3;

	@ManyToOne
	@JoinColumn(name = "createdby", referencedColumnName = "id")
	TicketUser createdBy;

	@ManyToOne
	@JoinColumn(name = "assignedto", referencedColumnName = "id")
	TicketUser assignedTo;

	@ManyToOne
	@JoinColumn(name = "customer", referencedColumnName = "id")
	TicketUser customer;

	@OneToMany(targetEntity = TicketResponse.class, mappedBy = "ticketid", cascade = { CascadeType.REMOVE })
	List<TicketResponse> responses;

	@Enumerated(EnumType.STRING)
	TicketStatus status = TicketStatus.OPEN;

	@Column(name = "createdtime")
	Long createdTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<TicketResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<TicketResponse> responses) {
		this.responses = responses;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TicketType getType() {
		return type;
	}

	public void setType(TicketType type) {
		this.type = type;
	}

	public TicketPriority getPriority() {
		return priority;
	}

	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	public TicketUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(TicketUser createdBy) {
		this.createdBy = createdBy;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public TicketUser getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(TicketUser assignedTo) {
		this.assignedTo = assignedTo;
	}

	public TicketUser getCustomer() {
		return customer;
	}

	public void setCustomer(TicketUser customer) {
		this.customer = customer;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

}
