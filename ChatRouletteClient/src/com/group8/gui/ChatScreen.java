package com.group8.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.group8.client.ChatClient;

public class ChatScreen extends JPanel{
	
	private ChatClient client; 
	private String name;
	
	private JLabel chatName; 
	private JTextField screen; 
	
	private ChatTextBoxPanel textPanel = new ChatTextBoxPanel(); 

	
	public ChatScreen(ChatClient c, String username)
	{
		JPanel mainPanel = new JPanel(); 
		
		client = c; 
		name = username; 
		chatName = new JLabel("Chatting with user: " + username);
		
		screen = new JTextField();
		screen.setPreferredSize(new Dimension(500, 400)); 
		screen.setEditable(false); 

		
		mainPanel.setLayout(new BorderLayout(10,10)); 
		mainPanel.add(chatName, BorderLayout.NORTH); 
		mainPanel.add(screen, BorderLayout.CENTER); 
		mainPanel.add(textPanel, BorderLayout.SOUTH); 

		add(mainPanel); 
		
		textPanel.sendButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	updateScreen(textPanel.getNewText()); 
            }
        });
		
		
	}

	public void updateScreen(String message)
	{
		String current = screen.getText(); 
		screen.setText(current+ "\n\n" +message); 
		//System.out.println(client);

		//client.onCommandEntered("send:nonie:message"); 


	}
}

class ChatTextBoxPanel extends JPanel {
	JPanel panel = new JPanel(); 
	
	private JTextField textBox; 
	private JButton sendButton; 
	
	public ChatTextBoxPanel()
	{
		textBox = new JTextField(); 
		textBox.setPreferredSize(new Dimension(500, 100)); 
		textBox.setVisible(true); 
		textBox.setEditable(true); 
		
		sendButton = new JButton("Send"); 
		
		panel.setLayout(new BorderLayout(10,10)); 
		
		panel.add(textBox, BorderLayout.NORTH);
		panel.add(sendButton, BorderLayout.SOUTH); 
		
		add(panel); 
		
	}
	
    public void sendButtonAddActionListener(ActionListener listener)
    {
      sendButton.addActionListener(listener);
      textBox.setText(""); 
    }
    
    public String getNewText() {
    	return textBox.getText(); 
    }
    
}
