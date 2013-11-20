package com.group8.gui;

import javax.swing.*;

import com.group8.client.ChatClient;
import com.group8.view.View;

import java.awt.*;
 
public class WaitList implements View {
 
	static ChatClient client; 
	public WaitList(ChatClient c)
	{
		client = c; 
		String[] args = null; 
		main(args); 
	}
	
	public void displayMessage( String msg ){}
	public void displayInfo(String info){}


	
	private static void createAndShowUI()
	{ 
		Login login = new Login(client);
        JTabbedPane tabs = new JTabbedPane();
        JFrame frame = new JFrame("ChatRoulette");
        tabs.add("Login Screen", login);
                
        frame.add(tabs, BorderLayout.CENTER);
        frame.setSize(600,700);
        frame.setVisible(true);
	}
	
	public static void main(String[] args) 
    {  
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI();
			}
		});
    }
}
