package com.satheesh.ticket.models;

import java.util.List;

public class Personalization {

	List<Email> to;

	public Personalization(List<Email> to) {
		super();
		this.to = to;
	}

	public List<Email> getTo() {
		return to;
	}

	public void setTo(List<Email> to) {
		this.to = to;
	}

}
