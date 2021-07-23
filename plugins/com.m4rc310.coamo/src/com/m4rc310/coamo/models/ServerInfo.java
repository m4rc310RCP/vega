package com.m4rc310.coamo.models;

import java.util.Calendar;

public class ServerInfo {
	private Calendar date;
	private String version;

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


}
