package com.group8.database;

import javax.jms.Destination;

public class User {
	private String username;
	private Destination destination;
	private boolean isOnline;
	
	public User(String username, Destination destination, boolean isOnline){
		this.username = username;
		this.destination = destination;
		this.isOnline = isOnline;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return this.username;
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
