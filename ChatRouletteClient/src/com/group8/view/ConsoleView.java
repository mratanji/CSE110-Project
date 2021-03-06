package com.group8.view;

import java.util.Scanner;

import com.group8.client.ChatClient;

public class ConsoleView extends Thread implements View{
	private ChatClient client;
	private Scanner myScanner;
	
	public ConsoleView(ChatClient client){
		this.client = client;
		myScanner = new Scanner(System.in);
		this.start();
	}
	
	public void run(){
		listenForInput();
	}
	
	private void listenForInput(){
		String inputString = myScanner.nextLine();
		client.onCommandEntered(inputString);
		listenForInput();
	}

	public void displayMessage(String msg){
		if(!msg.equals("update user list")){
			System.out.println(msg);
		}
	}

	public void displayInfo(String info){
		System.out.println(info);
	}
}

