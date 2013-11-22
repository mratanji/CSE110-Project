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
public class TestClient {
	
	public ChatClient client = new ChatClient();
	
	
	@Test
	public void testonCommandEntered()
	{
		
	}

	@Test
	public void testSend() {
		// Test if send() throws an exception when we pass a null argument
		fail("not yet implememnted");
	
		// Test if recipient is offline
		fail("Not yet implemented!");
	
		// Test blank messages (shouldn't send)
		fail("Not yet implemented!");
		
		// Test if message exceeds character limit
		fail("Not yet implemented!");
		
		// Test if a normal usage case message is sent properly
		fail("Not yet implemented!");
	
		// Test if the recipient is missing from "to" field
		fail("Not yet implemented!");
	
		// Test if client can broadcast a message to all signed on users
		fail("Not yet implemented!");
	}

	@Test
	public void testReceive(){
		// Check if the client can receive a normal message
		fail("Not yet implemented!");
	}
	@Test
	// Test if client has logged in correctly
	public void testLogin(){
		// Test if valid username and password are entered
		fail("Not yet implemented!");
		
		// Test if user doesnt enter in username and password
		fail("Not yet implemented!");
		
		// Test if user enters username but no password
		fail("Not yet implemented!");
		
		// Test if user doesn't enter username but password
		fail("Not yet implemented!");
		
		// Test if username and password are entered but the username is invalid
		fail("Not yet implemented!");
		
		// Test if the user enters in both username and password, but the password is invalid
		fail("Not yet implemented!");
		
		// Test if the backend is exposed in the login process, that is, usernames and passwords are exposed
		fail("Not yet implemented!");
		
		// Test if the server is unreachable for client verification
		fail("Not yet implemented!");
	}
	
	@Test
	public void testLogout(){
		// Check if client can't send messages anymore
		fail("Not yet implemented!");
	}
	
	@Test
	public void testStatus(){
		// Test if client is busy if client changes to "busy"
		fail("Not yet implemented!");
		
		// Test if client is unavailable if client changes to "unavailable"
		fail("Not yet implemented!");
		
		// Test if client is online if client changes to "online"
		fail("Not yet implemented!");
	}
	
	@Test
	public void testChatRoom(){
		// Test if client can create a chat room
		fail("Not yet implemented!");
		
		// Test if client can enter an existing chat room
		fail("Not yet implemented!");
		
		// Test if client can leave a current chat room
		fail("Not yet implemented!");
		
		// Test if client can send a message to all participants in a chat room
		fail("Not yet implemented!");
		
		// Test if client stops receiving messages from a chat room after leaving
		fail("Not yet implemented!");
	}
}
