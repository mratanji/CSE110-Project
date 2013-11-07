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
	
	// Returns false if the user log file cannot be created, shouldn't throw exceptions
	public boolean populateUserMap(){
		// Check if a log file already exists
		File userLog;
		Scanner s;
		try{
			userLog = new File("userLog.txt");
			s = new Scanner(userLog);
		} catch(Exception e){
			System.out.println("Error: couldn't populate the userLog!");
			return false;
		}
		if( !(userLog.exists())){
			try{ 
				userLog.createNewFile(); 
			}catch(Exception e){
				System.out.println("Error: couldn't createNewFile for userLog!");
				s.close();
				return false;}
		}

		else{
			// It exists, so now parse through the file and add user objects to the database
			while( s.hasNextLine()){
				String current = s.nextLine();
				String[] userElements = current.split(":", 2);
				// Add to the hashMap, the addUser method should handle duplicate cases
				this.addUser(userElements[0], userElements[1], null);
			}
		}
		s.close();
		return true;
	}

	//Returns false if user is already in the database, again, a lot of exception handling
	public boolean addUser(String username, String password, Destination destination){

		if(userMap.containsKey(username)){
			return false;
		}
		else{
			userMap.put(username, new User(username,password, destination, false));
			
			// Now, add the user to the userLog file
			FileOutputStream userLog;
			try{
				userLog = new FileOutputStream(new File("userLog.txt"), true);
			}catch(Exception e){
				System.out.println("Error: In addUser(): Couldn't open fileOutputStream!");
				return false;
			}
			PrintStream userLogStream = new PrintStream(userLog);
			userLogStream.println(username + ":" + password);
			userLogStream.close();
			return true;
		}
	}
	
	//Returns false if user is not in the database, a lot of exception handling done here
	public boolean removeUser(String username) {
		if(userMap.containsKey(username)){
			userMap.remove(username);
			
			// Now to remove it from the log file, this is long...
			// Construct temporary file
			File userLog = new File("userLog.txt");
			File tempLog = new File("userLogtemp.txt");
			BufferedReader reader;
			PrintWriter writer;
			try{
				reader = new BufferedReader (new FileReader(userLog));
				writer = new PrintWriter(new FileWriter (tempLog));
			}catch(Exception e){
				System.out.println("Error: In removeUser: Couldn't create buffer reader/writer!");
				return false;
			}
			String line = null;

			// Read from original, write to temporary and trim space, while user name is not found
			try{
				while((line = reader.readLine()) !=null) {
				    if(line.split(":", 1)[0].equals(username)){
				        continue;          }
				    else{
				        writer.println(line);
				        writer.flush();
				    }
				}
				// Close reader inside try-catch
				reader.close();
			}catch(Exception e){
				System.out.println("Error: In removeUser: Couldn't read nextLine or close reader!");
				writer.close();
				return false;
			}
			// close writer leaks
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
	public boolean signOnUser(String username, String password, Destination destination){
		if(userMap.containsKey(username)){
			User currentUser = userMap.get(username);
			if( !(currentUser.getPassword().equals(password)))
					return false; // The password was wrong!
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
