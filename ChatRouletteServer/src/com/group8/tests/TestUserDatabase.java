package com.group8.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.jms.Destination;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.group8.database.UserDatabase;

public class TestUserDatabase {
	
	UserDatabase users;
	Destination destination;

	//A setUp method that runs before every test method 
	@Before
	public void setUp()
	{
		users = new UserDatabase();
		destination = null;
	}

	// Checks if a username was added during the tests and removes it
	// PROBLEM: Cannot add these names to log file for it will cause errors for tests. 
	@After
	public void clearUser()
	{
		if(users.containsUser("Alexander"))
			users.removeUser("Alexander");
		if(users.containsUser("Stanley"))
			users.removeUser("Stanley");
		if(users.containsUser("Ryne"))
			users.removeUser("Ryne");
		if(users.containsUser("Monish"))
			users.removeUser("Monish");
		if(users.containsUser("Nonie"))
			users.removeUser("Nonie");
	}
	

	@Test
	public void testAddUser() {
		try{
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertFalse(users.addUser("Alexander", "password123", destination ));
		}catch(IllegalArgumentException e){fail();}
	}

	@Test
	public void testRemoveUser() {
		try{
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertTrue(users.removeUser("Alexander"));
			assertFalse(users.removeUser("Stanley"));
		}catch(IllegalArgumentException e){fail();}
	}

	@Test
	public void testSignOnUser() {
		try{	
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
			assertTrue(users.addUser("Alexander", "password123", destination ));
			assertTrue(users.containsUser("Alexander"));
		}
		catch(IllegalArgumentException e){fail();}
	}

	// Checks if getUserDestination works
	@Test
	public void testGetUserDestination() {
		try{
			assertTrue(users.addUser("Alexander", "password123", destination ));
			users.signOnUser("Alexander", "password123", destination);
			assertEquals(null, users.getUserDestination("Alexander"));	
		}
		catch(IllegalArgumentException e){fail();}
		
	}
	
	// Checks if returns false if try to get destination for someone not in database.
	@Test
	public void testGetUserDestination2()
	{
		try{
			assertTrue(users.addUser("Alexander", "password123", destination ));
			users.signOnUser("Alexander", "password123", destination);
			assertEquals(null, users.getUserDestination("Stanley"));
			fail();
			
		}
		catch(IllegalArgumentException e){}
		
	}

	// Tests if isUserOnline works
	@Test
	public void testIsUserOnline() {
		try{
			assertTrue(users.addUser("Alexander", "password123", destination ));
			users.signOnUser("Alexander", "password123", destination);
			assertTrue(users.isUserOnline("Alexander"));
			
		}
		catch(IllegalArgumentException e){fail();}

	}
	
	// Tests if checking for user not in the database will throw exception
	@Test
	public void testIsUserOnline2()
	{
		try{	
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
			
			String onlineUsers = "Online Users:\nAlexander\nMonish\nRyne\nStanley\n";
			
			assertEquals(onlineUsers, users.listAllUsers());
		}catch(IllegalArgumentException e){fail();}
		
		
		
		
		
	}
}
