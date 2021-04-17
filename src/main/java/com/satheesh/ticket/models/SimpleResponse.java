package com.satheesh.ticket.models;

public class SimpleResponse {

	int code;
	String message;

	public SimpleResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public SimpleResponse(String message) {
		this.code = 200;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SimpleResponse [code=" + code + ", message=" + message + "]";
	}

}
