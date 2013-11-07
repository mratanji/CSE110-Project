package com.group8.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import java.util.LinkedList;
import java.util.List;


import javax.jms.Destination;



public class UserDatabase {
	private HashMap<String, User> userMap;
	
	public UserDatabase(){
		userMap = new HashMap<String, User>();
	}
	

	/** Maybe these should all be void and throw exceptions instead of returning false 
	 * @throws IOException **/
	
	// Returns false if the user log file cannot be created
	public boolean populateUserMap() throws IOException{
		// Check if a log file already exists
		File userLog = new File("userLog.txt");
		Scanner s = new Scanner(userLog);
		if( !(userLog.exists())){
			userLog.createNewFile();
		}
		else{
			// It exists, so now parse through the file and add user objects to the database
			while( s.hasNextLine()){
				String current = s.nextLine();
				String[] userElements = current.split(" ", 2);
				// Add to the hashMap, the addUser method should handle duplicate cases
				this.addUser(userElements[0], userElements[1], null);
			}
		}
		s.close();
		return true;
	}

	//Returns false if user is already in the database
	public boolean addUser(String username, String password, Destination destination) throws FileNotFoundException{

		if(userMap.containsKey(username)){
			return false;
		}
		else{
			userMap.put(username, new User(username,password, destination, false));
			
			// Now, add the user to the userLog file
			FileOutputStream userLog = new FileOutputStream(new File("userLog.txt"), true);
			PrintStream userLogStream = new PrintStream(userLog);
			userLogStream.println(username + password);
			userLogStream.close();
			return true;
		}
	}
	
	//Returns false if user is not in the database
	public boolean removeUser(String username) throws IOException{
		if(userMap.containsKey(username)){
			userMap.remove(username);
			
			// Now to remove it from the log file, this is long...
			// Construct temporary file
			File userLog = new File("userLog.txt");
			File tempLog = new File("userLogtemp.txt");

			BufferedReader reader = new BufferedReader (new FileReader(userLog));
			PrintWriter writer = new PrintWriter(new FileWriter (tempLog));
			String line = null;

			//read from original, write to temporary and trim space, while title not found
			while((line = reader.readLine()) !=null) {
			    if(line.split(" ", 1)[0].equals(username)){
			        continue;          }
			    else{
			        writer.println(line);
			        writer.flush();
			    }
			}
			// close resource leaks
			reader.close();
			writer.close();

			// delete old file
			userLog.delete();

			// rename tempLog
			if(! (tempLog.renameTo(userLog)) ){
				return false;
			}
			// Done! Just return.
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
			if(this.isUserOnline(username))
				return false;
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
	
	public String listAllUsers()
	{
		List<String> onlineUsersList = new LinkedList<String>();  
		String onlineUsers = ""; 
		
		User[] allUsers = userMap.values().toArray(new User[userMap.size()]);
		
		for( int i = 0; i < allUsers.length; i++ )
		{
			if(allUsers[i].isOnline())
				onlineUsersList.add(allUsers[i].getUsername());
		}
		
		 java.util.Collections.sort(onlineUsersList);
		 for(String user : onlineUsersList)
		 {
			onlineUsers = onlineUsers + "\n" + user;
		 }
		return onlineUsers; 
	}
	
	public String[] getAllUsers()
	{
		List<String> onlineUsers = new LinkedList<String>();
		User[] allUsers = userMap.values().toArray(new User[userMap.size()]);
		
		for( int i = 0; i < allUsers.length; i++ )
		{
			if(allUsers[i].isOnline())
				onlineUsers.add( allUsers[i].getUsername() );
		}
		return (String[]) onlineUsers.toArray(new String[onlineUsers.size()]); 
	}
}
