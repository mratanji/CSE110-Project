package com.group8.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import com.group8.client.ChatClient;


public class GUIView extends JFrame implements View {

	private ChatClient client; 
	private JFrame frame; 
	private String[] listOfAllUsers; 
	
	private JPanel currentPanel; 
	
	//LOGIN Panel:
	private JLabel loginLabel;
    private JLabel userIDLabel;
    private JLabel passwordLabel;
	private JButton loginButton;
	private JButton newUserButton;
    private JTextField userIDTF;
    private JPasswordField passwordTF;
    
    //CREATE NEW USER Panel: 
	private JLabel createNewLabel;
    private JLabel newUserIDLabel;
    private JLabel newPasswordLabel;
    private JLabel confirmPasswordLabel; 
	private JButton createButton;
	private JButton backToLoginButton;
    private JTextField newUserIDTF;
    private JPasswordField newPasswordTF;
    private JPasswordField confirmPasswordTF;
    
    //CHAT MAIN Panel - this is the panel that lists online users: 
    	//Users: 
    private JLabel selectUser;
    private JButton goUserButton;   
    public JList<String> onlineUsers; 
    	//Chatrooms: 
	private JLabel selectChatRoom; 
    private JButton goChatroomButton;   
	private JList<String> chatrooms; 
    
    
	
	
	public GUIView(ChatClient c)
	{   
		client = c; 
        frame = new JFrame("ChatRoulette");
        currentPanel = makeLoginScreen();    
		frame.setContentPane(currentPanel); 
		frame.setSize(600,700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public JPanel makeNewUserScreen() 
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		//Labels: 
		createNewLabel = new JLabel("Create a new account");
	    newUserIDLabel = new JLabel("User ID");
	    newPasswordLabel = new JLabel("Password");
	    confirmPasswordLabel = new JLabel("Retype Password");
		
	    //Text fields for UserID and passwords: 
	    newUserIDTF = new JTextField();
	    newUserIDTF.setPreferredSize(new Dimension(100,20)); 
	    newPasswordTF = new JPasswordField();
	    newPasswordTF.setPreferredSize(new Dimension(100,20)); 
	    confirmPasswordTF = new JPasswordField();
	    confirmPasswordTF.setPreferredSize(new Dimension(100,20)); 

		
	    //Buttons: 
	    createButton = new JButton ("Create");
	    backToLoginButton = new JButton("Back to Login"); 
	    
	    JPanel textPanel = new JPanel(); 
        //textPanel.setLayout( new GridLayout(3, 2));
        // Add items to grid
        textPanel.add(newUserIDLabel);
        textPanel.add(newUserIDTF);
        textPanel.add(newPasswordLabel);
        textPanel.add(newPasswordTF);
        textPanel.add(confirmPasswordLabel);
        textPanel.add(confirmPasswordTF);
        
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout());
	    buttonPanel.add(createButton);
	    buttonPanel.add(backToLoginButton);
	    
	    panel.add(createNewLabel, BorderLayout.NORTH); 
	    panel.add(textPanel, BorderLayout.CENTER); 
	    panel.add(buttonPanel, BorderLayout.SOUTH); 
	    
        //Button action methods: 
        createButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(isNewUserValid(newPasswordTF.getText(), confirmPasswordTF.getText())) {
        			String commandMessage = new String("add-user:" + newUserIDTF.getText() + ":" + newPasswordTF.getText()); 
            		client.onCommandEntered(commandMessage); 
        		}
        	}
        }); 
        
        backToLoginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.remove(currentPanel); 
        		frame.revalidate(); 
        		currentPanel = makeLoginScreen(); 
        		frame.setContentPane(currentPanel); 
        		frame.setVisible(true);
        	}
        }); 
	    
		return panel; 
	}
	
	public JPanel makeLoginScreen() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
	    //Make the objects in the Login Screen: 
		loginLabel = new JLabel("Login"); 
	    userIDLabel = new JLabel("User ID");
	    passwordLabel = new JLabel("Password");
	    
		loginButton = new JButton("Login");
		newUserButton = new JButton("New User");
		
	    userIDTF = new JTextField();
	    userIDTF.setPreferredSize(new Dimension(100,20)); 
	    passwordTF = new JPasswordField();
	    passwordTF.setPreferredSize(new Dimension(100,20));
		
	    panel.add(loginLabel, BorderLayout.NORTH); 
	    
	    JPanel buttonPanel = new JPanel();
	    //buttonPanel.setLayout(new FlowLayout());
	    buttonPanel.add(loginButton);
	    buttonPanel.add(newUserButton);
	    
	    JPanel textPanel = new JPanel();
	    // textPanel.setPreferredSize(new Dimension(200,200));
	    // textPanel.setLayout(new GridLayout(2,2));
        // Add items to grid
        textPanel.add(userIDLabel);
        textPanel.add(userIDTF);
        textPanel.add(passwordLabel);
        textPanel.add(passwordTF);

        
        //Add title, text fields, and button onto screen: 
	    panel.add(loginLabel, BorderLayout.NORTH); 
	    panel.add(textPanel, BorderLayout.CENTER); 
	    panel.add(buttonPanel, BorderLayout.SOUTH); 
	    

        //Button action methods: 
        loginButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String commandMessage = new String("sign-on:"+userIDTF.getText()+":"+passwordTF.getText()); 
        		//TO DO Check if the user is online already: 
        		if(listOfAllUsers == null)
        			client.onCommandEntered(commandMessage);
        		else if(userAlreadyOn(userIDTF.getText()))
        			System.out.println("NOOO"); 
        		else
        			client.onCommandEntered(commandMessage);
        		
        		frame.remove(currentPanel); 
        		frame.revalidate(); 
        		currentPanel = makeChatMainScreen(); 
        		frame.setContentPane(currentPanel); 
        		frame.setVisible(true);
        	}
        }); 
        
        newUserButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { 
        		frame.remove(currentPanel); 
        		frame.revalidate(); 
        		currentPanel = makeNewUserScreen(); 
        		frame.setContentPane(currentPanel); 
        		frame.setVisible(true); 
        	}
        }); 

		return panel; 
	}
	
	
	public boolean isNewUserValid(String pass, String passConfirm) {
		if(pass.equals(passConfirm)) {
			JOptionPane.showMessageDialog(currentPanel, "Your account has been created");
			return true; 
		}
		JOptionPane.showMessageDialog(currentPanel, "Your password and the password confirmation do not match.");
		return false; 
	}
	
	public JPanel makeChatMainScreen() {
		JFrame newWindow = new JFrame(); 
		JPanel panel = new JPanel(); 
		panel.setLayout(new BorderLayout(20,20)); 
		
		client.onCommandEntered("list-all"); 
		
		JPanel usersPanel = new JPanel(); 
		usersPanel.setLayout(new BorderLayout(10,10)); 
		selectUser = new JLabel("Select a user to chat with:"); 
		usersPanel.add(selectUser, BorderLayout.NORTH); 
	  	onlineUsers = new JList();
	  	onlineUsers.setListData(listOfAllUsers);
	  	onlineUsers.setPreferredSize(new Dimension(300,200));
	  	usersPanel.add(onlineUsers, BorderLayout.CENTER); 
		goUserButton = new JButton("Start Chatting");  
		usersPanel.add(goUserButton, BorderLayout.SOUTH); 
	    	
	  	JPanel chatroomsPanel = new JPanel(); 
	  	chatroomsPanel.setLayout(new BorderLayout(10,10));
	  	selectChatRoom = new JLabel("Select a chatroom to join it."); 
	  	chatroomsPanel.add(selectChatRoom, BorderLayout.NORTH);
		chatrooms = new JList();
		chatrooms.setPreferredSize(new Dimension(300,200)); 
		chatroomsPanel.add(chatrooms, BorderLayout.CENTER); 
	   	goChatroomButton = new JButton("Start Chatting");
	   	chatroomsPanel.add(goChatroomButton, BorderLayout.SOUTH); 
	   	
	   	panel.add(usersPanel, BorderLayout.NORTH); 
	   	panel.add(chatroomsPanel, BorderLayout.CENTER); 
	   	
	   	goUserButton.addActionListener(new ActionListener() {
	   		public void actionPerformed(ActionEvent e) {
	   			//Create new chat with selected user: 
	   			String selectedUser = listOfAllUsers[onlineUsers.getSelectedIndex()]; 
	   			frame.remove(currentPanel); 
	   			currentPanel = makeChatScreen(); 
	   			frame.add(currentPanel); 
	   		}
	   	}); 
		
		return panel; 
	}
	
	public JPanel makeChatScreen() {
		JPanel panel = new JPanel(); 
		return panel; 
	}
	
	public boolean userAlreadyOn(String name)
	{
		for(int i = 0; i<listOfAllUsers.length-1; i++) {
			if(name.equals(listOfAllUsers[i]))
				return true; 
		}
		return false; 
	}

	@Override
	public void displayInfo(String info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMessage(String msg) {
		if(msg.contains("Online")) {
			System.out.println(msg);
	    	String[] userList = msg.split("\\n"); 
	    	String[] uList = new String[userList.length-1];
	    	for(int i = 1; i < userList.length; i++)
	    	{
	    		uList[i-1] = userList[i];
	    	}
	    	listOfAllUsers = uList; 
	    	System.out.println("SIZE" + listOfAllUsers.length);
		}
		else
		{
			//chatArea.setText(chatArea.getText()+"\n"+msg); 
		}
	}
	

}
