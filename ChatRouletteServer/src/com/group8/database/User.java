package com.group8.database;

import javax.jms.Destination;

public class User {
	private String username;
	private String password;
	private Destination destination;
	private boolean isOnline;
	
	public User(String username, String password, Destination destination, boolean isOnline){
		this.username = username;
		this.password = password;
		this.destination = destination;
		this.isOnline = isOnline;
	}
	
	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setDestination(Destination destination){
		this.destination = destination;
	}
	public Destination getDestination(){
		return this.destination;
	}
	
	public void setOnline(boolean isOnline){
		this.isOnline = isOnline;
	}
	public boolean isOnline(){
		return this.isOnline;
	}
	
	

}
