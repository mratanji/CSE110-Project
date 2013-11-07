package com.group8.client;

import java.util.HashMap;

public class CommandGroup {
	private HashMap<String, String> commandMap;
	
	public CommandGroup(){
		commandMap = new HashMap<String, String>();
		commandMap.put("add-user", "add-user");
		commandMap.put("remove-user", "remove-user");
		commandMap.put("sign-on", "sign-on");
		commandMap.put("sign-off", "sign-off");
		commandMap.put("send", "send");
		commandMap.put("broadcast", "broadcast");
		commandMap.put("list-all", "list-all");
	}
	
	public boolean containsCommand(String command){
		return commandMap.containsKey(command);
	}
}
