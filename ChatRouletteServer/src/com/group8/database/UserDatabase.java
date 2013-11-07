package com.group8.database;

import java.util.HashMap;

import javax.jms.Destination;

public class UserDatabase {
	private HashMap<String, User> userMap;
	
	public UserDatabase(){
		userMap = new HashMap<String, User>();
	}
	
	/** Maybe these should all be void and throw exceptions instead of returning false **/
	
	//Returns false if user is already in the database
	public boolean addUser(String username, Destination destination){
		if(userMap.containsKey(username)){
			return false;
		}
		else{
			userMap.put(username, new User(username, destination, false));
			return true;
		}
	}
	
	//Returns false if user is not in the database
	public boolean removeUser(String username){
		if(userMap.containsKey(username)){
			userMap.remove(username);
			return true;
		}
		else{
			return false;
		}
	}
	
	//Returns false if user is not in the database
	public boolean signOnUser(String username, Destination destination){
		if(userMap.containsKey(username)){
			User currentUser = userMap.get(username);
			currentUser.setOnline(true);
			currentUser.setDestination(destination);
			userMap.put(username, currentUser);
			return true;
		}
		else{
			return false;
		}
	}
	
	//Returns false if user is not in the database
	public boolean signOffUser(String username){
		if(userMap.containsKey(username)){
			User currentUser = userMap.get(username);
			currentUser.setOnline(false);
			userMap.put(username, currentUser);
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean containsUser(String username){
		return userMap.containsKey(username);
	}
	
	public Destination getUserDestination(String username){
		if(userMap.containsKey(username) && isUserOnline(username)){
			return userMap.get(username).getDestination();
		}
		else{
			throw new IllegalArgumentException();
		}
	}
	
	public boolean isUserOnline(String username){
		if(userMap.containsKey(username)){
			return userMap.get(username).isOnline();
		}
		else{
			throw new IllegalArgumentException();
		}
	}
}
