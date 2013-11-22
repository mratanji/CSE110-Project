package com.group8.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.jms.Destination;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.group8.database.ChatRoom;
import com.group8.database.User;

public class TestChatRoom {

	private String chatName = "ChatRoulette";
	private ChatRoom chat;
	private User host;
	private static User member1;
	private static User member2;
	
	@BeforeClass
	public static void makeUsers()
	{
		String name = "Monish";
		String password = "password123";
		Destination destination = null;
		boolean online = true; 
		member1 = new User(name, password, destination, online);
		
		String name2 = "Kenia";
		String password2 = "password123";
		Destination destination2 = null;
		boolean online2 = true; 
		member2 = new User(name2, password2, destination2, online2);	
	}
	
	
	@Before
	public void setUp()
	{
		String name = "Stanley";
		String password = "password123";
		Destination destination = null;
		boolean online = true; 
		host = new User(name, password, destination, online);
		
		chat = new ChatRoom(chatName, host.getUsername());
	}
	
	@Test
	public void testAddChatUser() {
		assertTrue(chat.addChatUser(member1.getUsername()));
		assertFalse(chat.addChatUser(host.getUsername()));
	}

	@Test
	public void testRemoveChatUser() {
		assertTrue(chat.addChatUser(member1.getUsername()));
		assertTrue(chat.removeChatUser(member1.getUsername()));
		assertFalse(chat.removeChatUser(member1.getUsername()));
	}

	//This one is not done yet still needs more for the test.
	@Test
	public void testGetChatUsers() {
		assertTrue(chat.addChatUser(member1.getUsername()));
		assertTrue(chat.addChatUser(member2.getUsername()));
		
		ArrayList<String> users;
		String[] users2 = {"Kenia", "Monish", "Stanley"};
		
		users = chat.getChatUsers();
		
		for(int i = 0; i < users2.length; i++)
			if(users.get(i) != users2[i])
				fail();
	}

	@Test
	public void testIsChatEmpty() {
		assertFalse(chat.isChatEmpty());
		assertTrue(chat.removeChatUser(host.getUsername()));
		assertTrue(chat.isChatEmpty());
	}

	@Test
	public void testGetChatName() {
		assertEquals(chatName, chat.getChatName());
	}

	@Test
	public void testRenameChat() {
		String newChatName = "RoulleteChat";
		chat.renameChat(newChatName);
		
		assertEquals(newChatName, chat.getChatName());
	}

}
