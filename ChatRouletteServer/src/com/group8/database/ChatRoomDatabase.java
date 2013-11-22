package com.group8.database;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ChatRoomDatabase {
	private HashMap<String, ChatRoom> roomMap;
	
	public ChatRoomDatabase(){
		this.roomMap = new HashMap<String, ChatRoom>();
	}
	
	public boolean addChatRoom( String name, User user){
		if( roomMap.containsKey(name))
			return false;
		roomMap.put(name, new ChatRoom(name, user));
		return true;
	}
	
	public boolean removeChatRoom(String name){
		if( !roomMap.containsKey(name))
			return false;
		roomMap.remove(name);
		return true;
	}
	
	public boolean renameChatRoom( String current, String newName){
		if( !roomMap.containsKey(current) || roomMap.containsKey(newName))
			return false;
		roomMap.get(current).renameChat(newName);
		roomMap.put(newName, roomMap.get(current));
		roomMap.remove(current);
		return true;
		
	}
	
	public String[] getChatRoomArray(){
		List<String> chatRooms = new LinkedList<String>();
		ChatRoom[] allChatRooms = roomMap.values().toArray(new ChatRoom[roomMap.size()]);
		
		for( int i = 0; i < allChatRooms.length; i++ )
			chatRooms.add( allChatRooms[i].getChatName() );

		return (String[]) chatRooms.toArray(new String[chatRooms.size()]); 
	}
	
	public String listChatRooms(){
		String chatList = "";
		String[] chatArrayList = this.getChatRoomArray();
		for( int i = 0; i < chatArrayList.length; i++)
			chatList = chatList + chatArrayList[i] + "\n";
		return chatList;
	}
	
	public ChatRoom getChatRoom( String name){
		return roomMap.get(name);
	}
}


