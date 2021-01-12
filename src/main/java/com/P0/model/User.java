package com.P0.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	private int UID;
	private String username;
	private String password;
	private byte[] salt;
	private String name;
	private Boolean perm;
	
	public User(int UID, String username, String password, byte[] salt, String name, Boolean perm) {
		this.UID = UID;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.name = name;
		this.perm = perm;
	}
	
	public boolean checkPassword(String password) {
		return password.equals(this.password);
	}
	
	public int getUID() {
		return UID;
	}
	
	public String getUsername() {
		return username;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonIgnore
	public byte[] getSalt() {
		return salt;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean getPerm() {
		return perm;
	}
}
