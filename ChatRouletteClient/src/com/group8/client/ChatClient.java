package com.group8.client;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.group8.view.*;

public class ChatClient {
	private MessageProducer producer;
	private Session session;
	private View view;
	
	public ChatClient(MessageProducer producer, Session session) {
		super();
		this.producer = producer;
		this.session = session;
		//Default the view to a consoleView for now.
		view = new ConsoleView(this);
		welcomeUser();
	} 
	
	private void send(String msg) throws JMSException {
		producer.send(session.createTextMessage(msg));
	}
	
	public void onCommandEntered(String command){
		//For now, just acknowledge that are MVP structure is set up correctly
		//In the future we will parse the command and perform the correct action
		view.displayInfo("Client received command: \"" + command + "\"");
	}
	
	private void welcomeUser(){
		view.displayInfo("Welcome to Chat Roulette!");
		displayHelp();
	}
	
	private void displayHelp(){
		view.displayInfo("\nInfo needed here to tell the user about what"
				       + " commands are allowed.\nEnter commands below:");
	}
}
