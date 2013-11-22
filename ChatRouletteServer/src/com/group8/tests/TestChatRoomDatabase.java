package com.group8.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.jms.Destination;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.group8.database.ChatRoom;
import com.group8.database.ChatRoomDatabase;
import com.group8.database.User;

public class TestChatRoomDatabase {
	
	private ChatRoomDatabase chatDatabase;
	private static User host;
	
	@BeforeClass
	public static void RoomSetUp()
	{
		String name = "Stanley";
		String password = "password123";
		Destination destination = null;
		boolean online = true; 
		host = new User(name, password, destination, online);
		
	}

	@Before
	public void setUp()
	{
		chatDatabase = new ChatRoomDatabase();
	}
	

	@Test
	public void testAddChatRoom() {
		assertTrue(chatDatabase.addChatRoom("Chat1", host.getUsername()));
		assertTrue(chatDatabase.addChatRoom("Chat2", host.getUsername()));
		assertFalse(chatDatabase.addChatRoom("Chat1", host.getUsername()));
	}

	@Test
	public void testRemoveChatRoom() {
		assertTrue(chatDatabase.addChatRoom("Chat1", host.getUsername()));
		assertTrue(chatDatabase.addChatRoom("Chat2", host.getUsername()));
		assertTrue(chatDatabase.removeChatRoom("Chat2"));
		assertTrue(chatDatabase.removeChatRoom("Chat1"));
		assertFalse(chatDatabase.removeChatRoom("Chat2"));
	}

	@Test
	public void testRenameChatRoom() {
		assertTrue(chatDatabase.addChatRoom("Chat1", host.getUsername()));
		assertTrue(chatDatabase.addChatRoom("Chat2", host.getUsername()));
		assertTrue(chatDatabase.renameChatRoom("Chat1", "Chat3"));
		assertFalse(chatDatabase.removeChatRoom("Chat1"));
		assertTrue(chatDatabase.removeChatRoom("Chat3"));
		assertFalse(chatDatabase.renameChatRoom("Nonexistant", "Chat3"));
		
	}
    
	@Test
	public void testGetChatRoomArray() {
		assertTrue(chatDatabase.addChatRoom("Chat1", host.getUsername()));
		assertTrue(chatDatabase.addChatRoom("Chat2", host.getUsername()));
		assertTrue(chatDatabase.addChatRoom("Chat3", host.getUsername()));
		assertTrue(chatDatabase.addChatRoom("Chat4", host.getUsername()));
		
		String[] chatList = chatDatabase.getChatRoomArray();
		String[] chatList2 = {"Chat3", "Chat2", "Chat1", "Chat4"};
		
		for(int i = 0; i < chatList2.length; i++)
			if(chatList[i] != chatList2[i])
				fail();
			
	}

	@Test
	public void testListChatRooms() {
		assertTrue(chatDatabase.addChatRoom("Chat1", host.getUsername()));
		assertTrue(chatDatabase.addChatRoom("Chat2", host.getUsername()));
		assertTrue(chatDatabase.addChatRoom("Chat3", host.getUsername()));
		assertTrue(chatDatabase.addChatRoom("Chat4", host.getUsername()));
		
		String chats = "Chat Rooms:\nChat3\nChat2\nChat1\nChat4\n";
		String chats2 = chatDatabase.listChatRooms();
		
		assertEquals(chats, chats2);
	}

	@Test
	public void testGetChatRoom() {
		ChatRoom check = new ChatRoom("Chat1", host.getUsername());
		ChatRoom chat1;
		String[] checkUser = {"Stanley"};
		ArrayList<String> chat1User;
		
		assertTrue(chatDatabase.addChatRoom("Chat1", host.getUsername()));
		
		chat1 = chatDatabase.getChatRoom("Chat1");
		assertEquals(check.getChatName(), chat1.getChatName());
		
		chat1User = chatDatabase.getChatRoom("Chat1").getChatUsers();
		
		for(int i = 0; i < chat1User.size(); i++)
			if(checkUser[i] != chat1User.get(i))
				fail();
	
		
	}

}
