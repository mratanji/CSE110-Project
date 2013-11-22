package com.group8.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChatRoom {
	
	private HashMap<String, String> userMap;
	private String name;
	
	public ChatRoom(String name, String username)
	{
		this.name = name;
		userMap = new HashMap<String,String>();
		userMap.put(username, username);
	}
	
	public boolean containsUser(String username){
		return userMap.containsKey(username);
	}
	
	public boolean addChatUser(String username)
	{
		if(userMap.containsValue(username))
			return false;
		
		userMap.put(username, username);
		return true;
	}
	
	
	public boolean removeChatUser(String username)
	{
		if(!userMap.containsValue(username)){
			return false;
		}
		userMap.remove(username);
		return true;
	}
	
	public ArrayList<String> getChatUsers()
	{
		ArrayList<String> userList = new ArrayList<String>();
		
		HashMap<String, String> newMap = new HashMap<String,String>(userMap);
		
		Iterator it = newMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        userList.add((String)pairs.getKey());
	        it.remove();
	    }
		
		return userList;
	}
	
	public String listUsers(){
		String userList = "Users in "+this.name+":\n";
		
		HashMap<String, String> newMap = new HashMap<String,String>(userMap);
		
		Iterator it = newMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        userList += pairs.getKey() + "\n";
	        it.remove();
	    }
		
		return userList;
	}
	
	public boolean isChatEmpty()
	{
		return userMap.isEmpty();
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
