package com.example.bulksms.models;

public class SaveFileClass {
	private String id;
	private String recipient;
	private String multi_recipient;
	private String message;
	private String fileName;

	public String getid() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String rep) {
		this.recipient = rep;
	}

	public String getMultiRecipiet() {
		return multi_recipient;
	}

	public void setMultiRecipient(String multRep) {
		this.multi_recipient = multRep;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String mes) {
		this.message = mes;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileN) {
		this.fileName = fileN;
	}
}