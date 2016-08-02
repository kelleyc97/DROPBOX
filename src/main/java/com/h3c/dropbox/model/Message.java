package com.h3c.dropbox.model;

import com.h3c.dropbox.entity.Box;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
	private String status;
	private String message;
	private String telecomUrl;
	private String unicomUrl;
	private String localUrl;
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getTelecomUrl() {
		return telecomUrl;
	}


	public void setTelecomUrl(String telecomUrl) {
		this.telecomUrl = telecomUrl;
	}


	public String getUnicomUrl() {
		return unicomUrl;
	}


	public void setUnicomUrl(String unicomUrl) {
		this.unicomUrl = unicomUrl;
	}


	public String getLocalUrl() {
		return localUrl;
	}


	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}


	public String getStatus() {
		return status;
	}

//
	public void setStatus(String status) {
		this.status = status;
	}


	
	public Message(String message, boolean createdBox, Box box) {
		this.message = message;
		if (createdBox) {
			status = "T";
			telecomUrl = "ftp://" + box.getBoxName() + ":" + box.getBoxPassword() + "@60.191.123.36";
			unicomUrl = "ftp://" + box.getBoxName() + ":" + box.getBoxPassword() + "@221.12.31.48";
			localUrl = "ftp://" + box.getBoxName() + ":" + box.getBoxPassword() + "@172.25.20.64";
		} else {
			status = "F";
			telecomUrl = "";
			unicomUrl = "";
			localUrl = "";
		}
	}
	
	public Message() {
		super();
	}
}
