package com.satheesh.ticket.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.satheesh.ticket.dao.TicketRepository;
import com.satheesh.ticket.dao.TicketResponseRepository;
import com.satheesh.ticket.entities.Ticket;
import com.satheesh.ticket.entities.TicketResponse;
import com.satheesh.ticket.entities.TicketUser;
import com.satheesh.ticket.models.Email;
import com.satheesh.ticket.models.EmailContent;
import com.satheesh.ticket.models.Personalization;
import com.satheesh.ticket.models.SendGridEmail;
import com.satheesh.ticket.models.TicketStatus;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketRepo;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	TicketResponseRepository ticketResponseRepo;

	private Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public List<Ticket> getAllTickets(String agent, String customer, String status) {
		List<Ticket> tickets = new ArrayList<>();
		if (agent == null && customer == null && status == null) {
			ticketRepo.findAll().forEach(ticket -> tickets.add(ticket));
		} else {
			ticketRepo.findAll(new Specification<Ticket>() {
				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<>();
					if (agent != null && !agent.isEmpty()) {
						predicates.add(root.get("assignedTo").get("name").in(agent));
					}
					if (customer != null && !customer.isEmpty()) {
						predicates.add(root.get("customer").get("name").in(customer));
					}
					if (status != null && !status.isEmpty()) {
						predicates.add(root.get("status").in(TicketStatus.valueOf(status)));
					}
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			}).forEach(ticket -> tickets.add(ticket));

		}
		return tickets;
	}

	@Override
	public Ticket getTicketById(int ticketId) {
		Optional<Ticket> result = ticketRepo.findById(ticketId);
		return result.get();
	}

	@Override
	public Ticket createTicket(Ticket ticket) {
		// If assignee is not provided, auto assign ticket to user with lowest ticket
		// count
		if (ticket.getAssignedTo() == null) {
			int userid = ticketRepo.getTicketUserWithLeastTicketCount();
			TicketUser user = new TicketUser();
			user.setId(userid);
			ticket.setAssignedTo(user);
		}
		ticket.setCreatedTime(System.currentTimeMillis() / 1000);
		return ticketRepo.save(ticket);
	}

	@Override
	public TicketResponse createTicketResponse(TicketResponse ticketResponse) {
		TicketResponse response = ticketResponseRepo.save(ticketResponse);
		Ticket ticketDetails = getTicketById(response.getTicketid());

		if (ticketDetails.getCustomer() != null && ticketDetails.getCustomer().getEmail() != null) {
			try {
				sendTicketResponseEmailToCustomer(ticketDetails.getCustomer().getEmail(), response.getResponse());
			} catch (Exception e) {
				logger.error(e);
			}
		}

		return response;

	}

	private void sendTicketResponseEmailToCustomer(String email, String response) {

		String url = "https://api.sendgrid.com/v3/mail/send";

		SendGridEmail emailTemplate = new SendGridEmail();
		List<Email> to = new ArrayList<>();
		to.add(new Email(email, email));
		Personalization personalization = new Personalization(to);

		List<Personalization> personalizations = new ArrayList<>();
		personalizations.add(personalization);

		emailTemplate.setPersonalizations(personalizations);
		emailTemplate.setFrom(new Email("Yogesh", "yogesh@sinecycle.com"));
		emailTemplate.setSubject("User Responded to the ticket");

		List<EmailContent> contents = new ArrayList<>();
		contents.add(new EmailContent("text/html", response));

		emailTemplate.setContent(contents);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer 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");

		HttpEntity<SendGridEmail> request = new HttpEntity<>(emailTemplate, headers);

		ResponseEntity<Object> resp = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
		if (resp.getStatusCodeValue() < 300 && resp.getStatusCodeValue() >= 200) {
			logger.info("Email Sent Successfully");
		} else {
			logger.info("Received " + resp.getStatusCodeValue() + " response from SendGrid.");
		}

	}

	@Override
	public void updateTicket(Ticket ticket) {
		if (ticketRepo.existsById(ticket.getId())) {
			ticketRepo.save(ticket);
		} else {
			throw new NoSuchElementException("Ticket not found");
		}
	}

	@Override
	public void deleteTicketById(int ticketId) {
		ticketRepo.deleteById(ticketId);
	}

}
