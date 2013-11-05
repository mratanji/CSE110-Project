/**
 * 
 */
package com.group8.client;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author mratanji
 *
 */
public class TestServer {

	@Test
	public void testLogin() {
		// Test if valid credentials can be found in the database
		fail("Not yet implemented");
		
		// Test if invalid credentials can not be found in the database
		fail("Not yet implemented");
		
		// Test if users status changes to "online" after logging in
		fail("Not yet implemented");
		
		// Test if users status changes to "off line" after logging out
		fail("Not yet implemented");
		
	}
	
	@Test
	public void testNewUser(){
		// Test if new user credentials are correctly added to the database
		fail("Not yet implemented");
	}
	
	@Test
	public void testConnection(){
		// Test if server connects two valid users
		fail("Not yet implemented!");
		
		// Test if server disconnects user after they log off
		fail("Not yet implemented!");
	}

	@Test
	public void testMessage(){
		// Test if server handles delivery of valid message to a valid recipient
		fail("Not yet implemented!");
		
		// Test if client sends message but server is busy
		fail("Not yet implemented!");
		
		// Test if server gracefully handles invalid recipient
		fail("Not yet implemented!");
		
		// Test if server is correctly logging messages (maybe implement?)
		fail("Not yet implemented!");
		
		// Test if sever is storing messages for off line users until they log back on
		fail("Not yet implemented!");
	}
	
	@Test
	public void testStatus(){
		// Test if server correctly broadcasts/updates a clients status
		fail("Not yet implemented!");
	}
	
	@Test
	public void testChat(){
		// Test if a user can be added to a current chat room
		fail("Not yet implemented!");
		
		// Test if a chat room can be created
		fail("Not yet implemented!");
		
		// Test if chat rooms are correctly instantiated on the server
		fail("Not yet implemented!");
		
		// ASSUMPTION!!!!		
		// Test sending of message to a specific subset of users =all users in a chat room
		fail("Not yet implemented!");
	}
}
