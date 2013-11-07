package com.group8.client;

import java.util.HashMap;

public class CommandGroup {
	private HashMap<String, String> commandMap;
	
	public CommandGroup(){
		commandMap = new HashMap<String, String>();
		commandMap.put("add-user", "add-user");
		commandMap.put("delete-my-account", "delete-my-account");
		commandMap.put("sign-on", "sign-on");
		commandMap.put("sign-off", "sign-off");
		commandMap.put("send", "send");
		commandMap.put("broadcast", "broadcast");
		commandMap.put("exit", "exit");
	}
	
	public boolean isValidCommand(String command){
		String[] commandComponents = command.split(":");
		if(commandMap.containsKey(command)){
			if(commandComponents[0].equals("add-user")){
				if(commandComponents.length != 2){
					return false;
				}
			}
			else if(commandComponents[0].equals("delete-my-account")){
				if(commandComponents.length != 1){
					return false;
				}
			}
			else if(commandComponents[0].equals("sign-on")){
				if(commandComponents.length != 2){
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
			else if(commandComponents[0].equals("exit")){
				if(commandComponents.length != 1){
					return false;
				}
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
