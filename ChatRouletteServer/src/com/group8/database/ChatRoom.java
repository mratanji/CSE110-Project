package com.group8.database;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ChatRoom {
	
	private HashMap<String,User> chatMap;
	private String name;
	
	public ChatRoom(String name, User user)
	{
		this.name = name;
		chatMap = new HashMap<String,User>();
		chatMap.put(user.getUsername(), user);
		
	}
	
	public boolean addChatUser(User user)
	{
		if(chatMap.containsKey(user.getUsername()))
			return false;
		
		chatMap.put(user.getUsername(), user);
		return true;
		
	}
	
	
	public boolean removeChatUser(User user)
	{
		if(!chatMap.containsKey(user.getUsername()))
			return false;
		
		chatMap.remove(user.getUsername());
		
		return true;
		
	}
	
	public String[] getChatUsers()
	{
		List<String> chatUsers = new LinkedList<String>();
		User[] allUsers = chatMap.values().toArray(new User[chatMap.size()]);
		
		for( int i = 0; i < allUsers.length; i++ )
		{
			if(allUsers[i].isOnline())
				chatUsers.add( allUsers[i].getUsername() );
		}
		return (String[]) chatUsers.toArray(new String[chatUsers.size()]); 
	}
	
	public boolean isChatEmpty()
	{
		return chatMap.isEmpty();
	}
	
	public String getChatName()
	{
		return name;
	}
	
	public void renameChat(String name)
	{
		this.name = name;
	}

}
