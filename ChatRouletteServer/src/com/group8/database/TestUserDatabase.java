package com.group8.database;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.jms.Destination;

import org.junit.Test;

public class TestUserDatabase {
	

	@Test
	public void testAddUser() {
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertFalse(users.addUser("Alexander", "password123", destination ));
		}catch(IllegalArgumentException e){fail();}
	}

	@Test
	public void testRemoveUser() {
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertTrue(users.removeUser("Alexander"));
			assertFalse(users.removeUser("Stanley"));
		}catch(IllegalArgumentException e){fail();}
	}

	@Test
	public void testSignOnUser() {
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertTrue(users.signOnUser("Alexander", "password123", destination));
			assertFalse(users.signOnUser("Stanley", "password123", destination));
			
			assertFalse(users.signOnUser("Alexander", "password123", destination));
		}
		catch(IllegalArgumentException e){fail();}
	}

	@Test
	public void testSignOffUser() {
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertTrue(users.signOffUser("Alexander"));
			assertFalse(users.signOffUser("Stanley"));
		}
		catch(IllegalArgumentException e){fail();}
		//assertFalse(users.signOffUser("Alexander"));		
	}

	@Test
	public void testContainsUser() {
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertTrue(users.containsUser("Alexander"));
		}
		catch(IllegalArgumentException e){fail();}
	}

	@Test
	public void testGetUserDestination() {
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertEquals(null, users.getUserDestination("Alexander"));	
		}
		catch(IllegalArgumentException e){fail();}
		
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertEquals(null, users.getUserDestination("Stanley"));
			fail();
			
		}
		catch(IllegalArgumentException e){fail();}
	}

	@Test
	public void testIsUserOnline() {
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", "password123", destination ));
			users.signOnUser("Alexander", "password123", destination);
			assertTrue(users.isUserOnline("Alexander"));
			
		}
		catch(IllegalArgumentException e){fail();}
		
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertFalse(users.isUserOnline("Stanley"));
			fail();
			
		}
		catch(IllegalArgumentException e){}
	}

	@Test
	public void testListAllOnlineUsers()
	{
		try{
			UserDatabase users = new UserDatabase();
		    Destination destination = null;	
			assertTrue(users.addUser("Alexander", "password123", destination ));
			users.signOnUser("Alexander", "password123", destination);
			assertTrue(users.addUser("Stanley", "password123", destination ));
			users.signOnUser("Stanley", "password123", destination);
			assertTrue(users.addUser("Ryne", "password123", destination ));
			users.signOnUser("Ryne", "password123", destination);
			assertTrue(users.addUser("Monish", "password123", destination ));
			users.signOnUser("Monish", "password123", destination);
			assertTrue(users.addUser("Nonie", "password123", destination ));
			users.signOffUser("Nonie");
			
			String onlineUsers = "\nAlexander\nMonish\nRyne\nStanley";
			
			assertEquals(onlineUsers, users.listAllUsers());
		}catch(IllegalArgumentException e){fail();}
		
		
		
		
		
	}
}
