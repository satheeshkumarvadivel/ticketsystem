package com.satheesh.ticket.models;

import java.util.List;

public class SendGridEmail {

	List<Personalization> personalizations;
	Email from;
	Email reply_to;
	String subject;
	List<EmailContent> content;

	public List<Personalization> getPersonalizations() {
		return personalizations;
	}

	public void setPersonalizations(List<Personalization> personalizations) {
		this.personalizations = personalizations;
	}

	public Email getFrom() {
		return from;
	}

	public void setFrom(Email from) {
		this.from = from;
	}

	public Email getReply_to() {
		return reply_to;
	}

	public void setReply_to(Email reply_to) {
		this.reply_to = reply_to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<EmailContent> getContent() {
		return content;
	}

	public void setContent(List<EmailContent> content) {
		this.content = content;
	}

}
