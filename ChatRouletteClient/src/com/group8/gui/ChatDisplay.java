package com.group8.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.group8.client.ChatClient;

public class ChatDisplay extends JPanel {

	JPanel panel = new JPanel(); 
	
	private ChatClient client; 
	private ListUserPanel userPanel; 
	private ListChatRoomPanel roomPanel; 
	
	public ChatDisplay(ChatClient c)
	{
        JPanel panel = new JPanel(); 

		client = c; 
		userPanel = new ListUserPanel(c); 
		roomPanel = new ListChatRoomPanel(c); 

        panel.setLayout(new BorderLayout(30,30));       
        
         panel.add(userPanel, BorderLayout.NORTH);
         panel.add(roomPanel, BorderLayout.SOUTH);

         add(panel);
         
         userPanel.goUserButtonAddActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e){
            	 //Create new chat with specified user: 
            	 String select = client.listUsersForGui()[userPanel.onlineUsers.getSelectedIndex()];
            	 System.out.println(select); 
            	 
             	//new ChatDisplay(client); 
             	JFrame jf = new JFrame();
             	jf.setSize(600,700);  
             	/*TODO: Creat a new window for every chat, create file: ChatScreenDisplay*/
             	//jf.add(new ChatScreenDisplay(client, selectedUser)); 
             	jf.setVisible(true); 
            	 
            	 //client.onCommandEntered(SEND MESSAGE TO USER WITH INDEX)

             }
         });
		
	}
	
	
}




class ListUserPanel extends JPanel
{    
    JPanel panel = new JPanel();
    
    private JLabel selectUser;
    private JButton goUserButton;   
    public JList onlineUsers = new JList(); 
    private ChatClient client; 
    
    public ListUserPanel(ChatClient c) {
    	client = c; 
    	
    	selectUser = new JLabel("Select a user to chat with."); 
    	//panel.add(selectUser);
        
        client.onCommandEntered("list-all"); 
        
        String listData[] = client.listUsersForGui();
        
        onlineUsers.setListData(listData);  
        onlineUsers.setPreferredSize(new Dimension(300,200)); 
        onlineUsers.setVisible(true); 

        
        goUserButton = new JButton("Start Chat"); 

        panel.setLayout(new BorderLayout(10,10));       
        
        panel.add(selectUser, BorderLayout.NORTH);
        panel.add(onlineUsers, BorderLayout.CENTER); 
        panel.add(goUserButton, BorderLayout.SOUTH);
        
        add(panel);
    }
    
    public void goUserButtonAddActionListener(ActionListener listener)
    {
      goUserButton.addActionListener(listener);
    }
    
    public void userToChatWith()
    {
    	int selectedIndex = onlineUsers.getSelectedIndex(); 
    	
    }

  }

class ListChatRoomPanel extends JPanel
{
	JPanel panel = new JPanel(); 
	
	private JLabel selectChatRoom = new JLabel("Select a chatroom to join it."); 
    private JButton goChatroomButton;   
	private JList chatrooms; 
	private ChatClient client; 
	
	public ListChatRoomPanel(ChatClient c) {
		client = c; 
		String listRooms[] = c.listChatRoomsForGui(); 
		chatrooms = new JList(listRooms); 
        chatrooms.setPreferredSize(new Dimension(300,200)); 

		//panel.add(chatrooms); 
		
        goChatroomButton = new JButton("Start Chat"); 
        //panel.add(goChatroomButton); 
		
        panel.setLayout(new BorderLayout(10,10)); 
        panel.add(selectChatRoom, BorderLayout.NORTH);
        panel.add(chatrooms, BorderLayout.CENTER); 
        panel.add(goChatroomButton, BorderLayout.SOUTH);
        
        
		add(panel);
	}
	
    public void goChatroomButtonAddActionListener(ActionListener listener)
    {
      goChatroomButton.addActionListener(listener);
    }

	
}
