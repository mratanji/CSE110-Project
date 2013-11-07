package com.group8.database;

import static org.junit.Assert.*;

import javax.jms.Destination;

import org.junit.Test;

public class TestUserDatabase {
	

	@Test
	public void testAddUser() {
	
		
		UserDatabase users = new UserDatabase();
	    Destination destination = null;	
		assertTrue(users.addUser("Alexander", destination ));
		assertFalse(users.addUser("Alexander", destination ));
		
	}

	@Test
	public void testRemoveUser() {
		UserDatabase users = new UserDatabase();
	    Destination destination = null;	
		assertTrue(users.addUser("Alexander", destination ));
		assertTrue(users.removeUser("Alexander"));
		assertFalse(users.removeUser("Stanley"));
	}

	@Test
	public void testSignOnUser() {
		UserDatabase users = new UserDatabase();
	    Destination destination = null;	
		assertTrue(users.addUser("Alexander", destination ));
		assertTrue(users.signOnUser("Alexander", destination));
		assertFalse(users.signOnUser("Stanley", destination));
		
		assertFalse(users.signOnUser("Alexander", destination));
		
	}

	@Test
	public void testSignOffUser() {
		UserDatabase users = new UserDatabase();
	    Destination destination = null;	
		assertTrue(users.addUser("Alexander", destination ));
		assertTrue(users.signOffUser("Alexander"));
		assertFalse(users.signOffUser("Stanley"));
		
		//assertFalse(users.signOffUser("Alexander"));		
	}

	@Test
	public void testContainsUser() {
		UserDatabase users = new UserDatabase();
	    Destination destination = null;	
		assertTrue(users.addUser("Alexander", destination ));
		assertTrue(users.containsUser("Alexander"));
	}

	@Test
	public void testGetUserDestination() {
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", destination ));
			assertEquals(null, users.getUserDestination("Alexander"));
			
		}catch(IllegalArgumentException e)
		{
			fail();
		}
		
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", destination ));
			assertEquals(null, users.getUserDestination("Stanley"));
			fail();
			
		}catch(IllegalArgumentException e){}
	}

	@Test
	public void testIsUserOnline() {
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", destination ));
			users.signOnUser("Alexander", destination);
			assertTrue(users.isUserOnline("Alexander"));
			
		}catch(IllegalArgumentException e)
		{
			fail();
		}
		
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", destination ));
			assertFalse(users.isUserOnline("Stanley"));
			fail();
			
		}catch(IllegalArgumentException e){}
		
	}

	@Test
	public void testListAllOnlineUsers()
	{
		UserDatabase users = new UserDatabase();
	    Destination destination = null;	
		assertTrue(users.addUser("Alexander", destination ));
		users.signOnUser("Alexander", destination);
		assertTrue(users.addUser("Stanley", destination ));
		users.signOnUser("Stanley", destination);
		assertTrue(users.addUser("Ryne", destination ));
		users.signOnUser("Ryne", destination);
		assertTrue(users.addUser("Monish", destination ));
		users.signOnUser("Monish", destination);
		assertTrue(users.addUser("Nonie", destination ));
		users.signOffUser("Nonie");
		
		String onlineUsers = "\nAlexander\nMonish\nRyne\nStanley";
		
		assertEquals(onlineUsers, users.listAllUsers());
		
		
		
		
		
	}
}
