package com.group8.gui;

import javax.swing.*;
import java.awt.*;
 
public class WaitList {
 
	private static void createAndShowUI()
	{ 
		Student student = new Student();
        JTabbedPane tabs = new JTabbedPane();
        JFrame frame = new JFrame("ChatRoulette");
        tabs.add("Login Screen", student);
                
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