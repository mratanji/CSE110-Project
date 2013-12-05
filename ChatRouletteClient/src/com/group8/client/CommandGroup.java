package com.group8.client;

import java.util.HashMap;

public class CommandGroup {
	private HashMap<String, String> commandMap;
	
	public CommandGroup(){
		commandMap = new HashMap<String, String>();
		commandMap.put("add-user", "add-user");//
		commandMap.put("delete-my-account", "delete-my-account");//
		commandMap.put("sign-on", "sign-on");//
		commandMap.put("sign-off", "sign-off");//
		commandMap.put("send", "send");//
		commandMap.put("chat", "chat");//
		commandMap.put("broadcast", "broadcast");//
		commandMap.put("group", "group");
		commandMap.put("exit", "exit");//
		commandMap.put("list-all", "list-all");//
		commandMap.put("add-chat-room", "add-chat-room");//
		commandMap.put("remove-chat-room", "remove-chat-room");//
		commandMap.put("list-all-chat-rooms", "list-all-chat-rooms");//
		commandMap.put("join-chat-room", "join-chat-room");//
		commandMap.put("leave-chat-room", "leave-chat-room");//
		commandMap.put("list-chat-room-users", "list-chat-room-users");
		commandMap.put("list-my-chat-rooms", "list-my-chat-rooms");//
		commandMap.put("gui", "gui");//
	}
	
	public boolean isValidCommand(String command){
		String[] commandComponents = command.split(":");
		if(commandMap.containsKey(commandComponents[0])){
			if(commandComponents[0].equals("add-user")){
				if(commandComponents.length != 3){
					return false;
				}
			}
			else if(commandComponents[0].equals("delete-my-account")){
				if(commandComponents.length != 1){
					return false;
				}
			}
			else if(commandComponents[0].equals("sign-on")){
				if(commandComponents.length != 3){
					return false;
				}
			}
			else if(commandComponents[0].equals("sign-off")){
				if(commandComponents.length != 1){
					return false;
				}
			}
			else if(commandComponents[0].equals("send")){
				if(commandComponents.length != 3){
					return false;
				}
			}
			else if(commandComponents[0].equals("group")){
				if(commandComponents.length != 3){
					return false;
				}
			}
			else if(commandComponents[0].equals("exit")){
				if(commandComponents.length != 1){
					return false;
				}
			}
			else if(commandComponents[0].equals("list-all")){
				if(commandComponents.length != 1){
					return false;
				}
			}
			else if(commandComponents[0].equals("broadcast")){
				if(commandComponents.length != 2){
					return false;
				}
			}
			else if(commandComponents[0].equals("add-chat-room")){
				if(commandComponents.length != 2){
					return false;
				}
			}
			else if(commandComponents[0].equals("remove-chat-room")){
				if(commandComponents.length != 2){
					return false;
				}
			}
			else if(commandComponents[0].equals("list-all-chat-rooms")){
				if(commandComponents.length != 1){
					return false;
				}
			}
			else if(commandComponents[0].equals("join-chat-room")){
				if(commandComponents.length != 2){
					return false;
				}
			}
			else if(commandComponents[0].equals("leave-chat-room")){
				if(commandComponents.length != 2){
					return false;
				}
			}
			else if(commandComponents[0].equals("list-chat-room-users")){
				if(commandComponents.length != 2){
					return false;
				}
			}
			else if(commandComponents[0].equals("list-my-chat-rooms")){
				if(commandComponents.length != 1){
					return false;
				}
			}
			else if(commandComponents[0].equals("chat")){
				if(commandComponents.length != 3){
					return false;
				}
			}
			else if(commandComponents[0].equals("gui")){
				if(commandComponents.length != 1){
					return false;
				}
				boolean gui = true;
				ChatClient chat = new ChatClient(gui);
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
		return true;
	}
}

