package com.P0.model;

import java.sql.Date;

public class Dino {
	private int dinoID;
	private String name;
	private String period;
	private Date date;
	private int userID;
	
	public Dino(int dinoID, String name, String period, int userID, Date date) {
		this.dinoID = dinoID;
		this.name = name;
		this.period = period;
		this.userID = userID;
		this.date = date;
	}
	
	public Dino(int dinoID, String name, String period, int userID) {
		this.dinoID = dinoID;
		this.name = name;
		this.period = period;
		this.userID = userID;
		this.date = Date.valueOf(java.time.LocalDate.now());
	}
	
	public int getDinoID() {
		return dinoID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPeriod() {
		return period;
	}

	public Date getDate() {
		return date;
	}

	public int getUserID() {
		return userID;
	}
}
