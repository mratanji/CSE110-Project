package com.group8.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import com.group8.client.ChatClient;

public class GUIView extends JFrame implements View {

	private ChatClient client;
	private JFrame frame;
	private String[] listOfAllUsers;
	private String[] listOfAllChatRooms;

	private JPanel currentPanel;
	
	//List Of Private Chats
	private HashMap<String, JPanel> tabMap;

	// LOGIN Panel:
	private JLabel loginLabel;
	private JLabel userIDLabel;
	private JLabel passwordLabel;
	private JButton loginButton;
	private JButton newUserButton;
	private JTextField userIDTF;
	private JPasswordField passwordTF;

	// CREATE NEW USER Panel:
	private JLabel createNewLabel;
	private JLabel newUserIDLabel;
	private JLabel newPasswordLabel;
	private JLabel confirmPasswordLabel;
	private JButton createButton;
	private JButton backToLoginButton;
	private JTextField newUserIDTF;
	private JPasswordField newPasswordTF;
	private JPasswordField confirmPasswordTF;

	// CHAT MAIN Panel - this is the panel that lists online users:
	private JTabbedPane tabbedPane;
	private JLabel selectUser;
	private JButton goUserButton;
	public JList<String> onlineUsers;
	// Chatrooms:
	private JLabel selectChatRoom;
	private JButton goChatroomButton;
	private JList<String> chatrooms;
	
	private JTextField addRemoveChatRoomTF;
	private JButton addChatRoomButton;
	private JButton removeChatRoomButton;
	
	private JTextArea broadcastTextArea;
	private JButton broadcastButton;
	
	private JButton signOffButton;
	private JButton deleteAccountButton;
	//End Main Panel
	

	

	public GUIView(ChatClient c) {
		client = c;
		frame = new JFrame("ChatRoulette");
		currentPanel = makeLoginScreen();
		frame.setContentPane(currentPanel);
		frame.setSize(600, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		tabMap = new HashMap<String, JPanel>();
	}

	public JPanel makeNewUserScreen() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// Labels:
		createNewLabel = new JLabel("Create a new account");
		newUserIDLabel = new JLabel("User ID");
		newPasswordLabel = new JLabel("Password");
		confirmPasswordLabel = new JLabel("Retype Password");

		// Text fields for UserID and passwords:
		newUserIDTF = new JTextField();
		newUserIDTF.setPreferredSize(new Dimension(100, 20));
		newPasswordTF = new JPasswordField();
		newPasswordTF.setPreferredSize(new Dimension(100, 20));
		confirmPasswordTF = new JPasswordField();
		confirmPasswordTF.setPreferredSize(new Dimension(100, 20));

		// Buttons:
		createButton = new JButton("Create");
		backToLoginButton = new JButton("Back to Login");

		JPanel textPanel = new JPanel();
		// textPanel.setLayout( new GridLayout(3, 2));
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

		// Button action methods:
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isNewUserValid(newPasswordTF.getText(),
						confirmPasswordTF.getText())) {
					String commandMessage = new String("add-user:"
							+ newUserIDTF.getText() + ":"
							+ newPasswordTF.getText());
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

		JPanel iconPanel = new JPanel();
		iconPanel.setPreferredSize(new Dimension(600, 250));
		JLabel welcomeLabel = new JLabel("Welcome", JLabel.CENTER);
		welcomeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		welcomeLabel.setPreferredSize(new Dimension(600, 50));
		iconPanel.add(welcomeLabel);
		URL url = getClass().getResource("logo-smaller.png");
	    ImageIcon imageicon = new ImageIcon(url);
	    JLabel imageLabel = new JLabel(imageicon);
	    iconPanel.add(imageLabel);
	    panel.add(iconPanel);
		
		JPanel userIdPanel = new JPanel();
		userIdPanel.setPreferredSize(new Dimension(600, 50));
		userIDLabel = new JLabel("User ID: ");
		userIDLabel.setPreferredSize(new Dimension(100, 50));
		userIDTF = new JTextField(40);
		userIdPanel.add(userIDLabel);
		userIdPanel.add(userIDTF);
		panel.add(userIdPanel);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setPreferredSize(new Dimension(600, 50));
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setPreferredSize(new Dimension(100, 50));
		passwordTF = new JPasswordField(40);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTF);
		panel.add(passwordPanel);

		

		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(600, 300));
		loginButton = new JButton("Login");
		loginButton.setPreferredSize(new Dimension(300, 30));
		JPanel blankPanel = new JPanel();
		blankPanel.setPreferredSize(new Dimension(600, 200));
		newUserButton = new JButton("New User");
		newUserButton.setPreferredSize(new Dimension(300, 30));
		buttonPanel.add(loginButton);
		buttonPanel.add(blankPanel);
		buttonPanel.add(newUserButton);
		panel.add(buttonPanel);

		// Button action methods:
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String commandMessage = new String("sign-on:"
						+ userIDTF.getText() + ":" + passwordTF.getText());
				// TO DO Check if the user is online already:
				client.onCommandEntered(commandMessage);
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
		if (pass.equals(passConfirm)) {
			JOptionPane.showMessageDialog(currentPanel,
					"Your account has been created");
			return true;
		}
		JOptionPane.showMessageDialog(currentPanel,
				"Your password and the password confirmation do not match.");
		return false;
	}

	public JPanel makeChatMainScreen() {
		JPanel panel = new JPanel();

		this.updateLists();
		
		JPanel iconPanel = new JPanel();
		iconPanel.setPreferredSize(new Dimension(600, 30));
		URL url = getClass().getResource("logo-smallest.png");
	    ImageIcon imageicon = new ImageIcon(url);
	    JLabel imageLabel = new JLabel(imageicon);
	    JLabel chatRouletteLabel = new JLabel("Chat Roulette ");
	    iconPanel.add(chatRouletteLabel);
	    iconPanel.add(imageLabel);
	    panel.add(iconPanel);

		int panelWidth = 290;
		JPanel usersPanel = new JPanel();
		usersPanel.setPreferredSize(new Dimension(panelWidth, 300));
		selectUser = new JLabel("Available Users:");
		selectUser.setPreferredSize(new Dimension(panelWidth, 15));
		usersPanel.add(selectUser);

		onlineUsers = new JList<String>();
		onlineUsers.setPreferredSize(new Dimension(panelWidth, 230));
		onlineUsers.setBorder(BorderFactory.createLineBorder(Color.black));
		usersPanel.add(onlineUsers);

		goUserButton = new JButton("Start Chatting");
		goUserButton.setPreferredSize(new Dimension(panelWidth, 40));
		usersPanel.add(goUserButton);
		panel.add(usersPanel);

		JPanel chatRoomPanel = new JPanel();
		chatRoomPanel.setPreferredSize(new Dimension(panelWidth, 300));
		selectChatRoom = new JLabel("Available Chat Rooms:");
		selectChatRoom.setPreferredSize(new Dimension(panelWidth, 15));
		chatRoomPanel.add(selectChatRoom);

		chatrooms = new JList<String>();
		chatrooms.setPreferredSize(new Dimension(panelWidth, 230));
		chatrooms.setBorder(BorderFactory.createLineBorder(Color.black));
		chatRoomPanel.add(chatrooms);

		goChatroomButton = new JButton("Join Chat Room");
		goChatroomButton.setPreferredSize(new Dimension(panelWidth, 40));
		chatRoomPanel.add(goChatroomButton);
		panel.add(chatRoomPanel);
		
		JPanel separator1 = new JPanel();
		separator1.setPreferredSize(new Dimension(600, 45));
		panel.add(separator1);
		
		broadcastTextArea = new JTextArea(5, 36);
		broadcastTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
		JScrollPane scrollie = new JScrollPane (broadcastTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollie);
		
		broadcastButton = new JButton("Broadcast");
		broadcastButton.setPreferredSize(new Dimension(150, 80));
		panel.add(broadcastButton);	
		
		JPanel separator2 = new JPanel();
		separator2.setPreferredSize(new Dimension(600, 45));
		panel.add(separator2);
		
		addRemoveChatRoomTF = new JTextField(20);
		addChatRoomButton = new JButton("Add Chat Room");
		removeChatRoomButton = new JButton("Remove Chat Room");
		panel.add(addRemoveChatRoomTF);
		panel.add(addChatRoomButton);
		panel.add(removeChatRoomButton);
		
		JPanel separator = new JPanel();
		separator.setPreferredSize(new Dimension(600, 45));
		panel.add(separator);
		
		signOffButton = new JButton("Sign Off/Exit");
		deleteAccountButton = new JButton("Delete Account");
		panel.add(signOffButton);
		panel.add(deleteAccountButton);
		
		broadcastButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.onCommandEntered("broadcast:" + broadcastTextArea.getText());
				broadcastTextArea.setText("");
			}
		});
		
		signOffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.onCommandEntered("exit");
			}
		});
		
		deleteAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.onCommandEntered("delete-my-account");
				client.onCommandEntered("exit");
			}
		});
		
		addChatRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.onCommandEntered("add-chat-room:" + addRemoveChatRoomTF.getText());
				String selectedChatRoom = addRemoveChatRoomTF.getText();
				addRemoveChatRoomTF.setText("");
				if(!tabMap.containsKey(selectedChatRoom)){
					JPanel p = makeChatRoomScreen(selectedChatRoom);
					tabMap.put(selectedChatRoom, p);
					tabbedPane.add(p);
					tabbedPane.setSelectedComponent(p);
				}
			}
		});
		
		removeChatRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.onCommandEntered("remove-chat-room:" + addRemoveChatRoomTF.getText());
				addRemoveChatRoomTF.setText("");
			}
		});

		goUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create new chat with selected user:
				String selectedUser = listOfAllUsers[onlineUsers.getSelectedIndex()];
				if(!tabMap.containsKey(selectedUser)){
					JPanel p = makeChatScreen(selectedUser);
					tabMap.put(selectedUser, p);
					tabbedPane.add(p);
					tabbedPane.setSelectedComponent(p);
				}	
			}
		});
		
		goChatroomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create new chat with selected user:
				String selectedChatRoom = listOfAllChatRooms[chatrooms.getSelectedIndex()];
				if(!tabMap.containsKey(selectedChatRoom)){
					JPanel p = makeChatRoomScreen(selectedChatRoom);
					tabMap.put(selectedChatRoom, p);
					tabbedPane.add(p);
					tabbedPane.setSelectedComponent(p);
					client.onCommandEntered("join-chat-room:" + selectedChatRoom);
				}
			}
		});
		return panel;
	}
	
	public JPanel makeChatScreen(final String name){
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(600, 700));
		p.setName(name);
		
		JButton exitButton = new JButton("Exit Chat");
		exitButton.setPreferredSize(new Dimension(580, 20));
		p.add(exitButton);
		
		final JTextArea textArea = new JTextArea(34, 50);
		textArea.setBorder(BorderFactory.createLineBorder(Color.black));
		textArea.setEditable(false);
		JScrollPane scroll1 = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p.add(scroll1);
		
		final JTextArea textField = new JTextArea(5, 40);
		textField.setBorder(BorderFactory.createLineBorder(Color.black));
		JScrollPane scroll2 = new JScrollPane (textField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p.add(scroll2);
		
		JButton sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(100, 80));
		p.add(sendButton);		
		
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.onCommandEntered("send:"+name+":"+textField.getText());
				textArea.append(client.username + ": " + textField.getText() + "\n\n");
				textArea.setCaretPosition(textArea.getDocument().getLength());
				textField.setText("");
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.remove(tabMap.get(name));
				tabMap.remove(name);
			}
		});
		
		return p;
	}
	
	public JPanel makeChatRoomScreen(final String name){
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(600, 700));
		p.setName(name);
		
		JButton exitButton = new JButton("Leave Chat Room");
		exitButton.setPreferredSize(new Dimension(580, 20));
		p.add(exitButton);
		
		final JTextArea textArea = new JTextArea(34, 50);
		textArea.setBorder(BorderFactory.createLineBorder(Color.black));
		textArea.setEditable(false);
		JScrollPane scroll1 = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p.add(scroll1);
		
		final JTextArea textField = new JTextArea(5, 40);
		textField.setBorder(BorderFactory.createLineBorder(Color.black));
		JScrollPane scroll2 = new JScrollPane (textField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p.add(scroll2);
		
		JButton sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(100, 80));
		p.add(sendButton);		
		
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.onCommandEntered("chat:"+name+":"+textField.getText());
				textArea.append(client.username + ": " + textField.getText() + "\n\n");
				textArea.setCaretPosition(textArea.getDocument().getLength());
				textField.setText("");
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.onCommandEntered("leave-chat-room:"+name);
				tabbedPane.remove(tabMap.get(name));
				tabMap.remove(name);
			}
		});
		
		return p;
	}

	public boolean userAlreadyOn(String name) {
		for (int i = 0; i < listOfAllUsers.length - 1; i++) {
			if (name.equals(listOfAllUsers[i]))
				return true;
		}
		return false;
	}

	public void setListOfAllUsers(String msg) {
		String[] userList = msg.split("\\n");
		listOfAllUsers = new String[userList.length - 1];
		for (int i = 1; i < userList.length; i++) {
			listOfAllUsers[i - 1] = userList[i];
		}
		onlineUsers.setListData(listOfAllUsers);
	}

	public void setListOfAllChatRooms(String msg) {
		String[] chatRoomList = msg.split("\\n");
		listOfAllChatRooms = new String[chatRoomList.length - 1];
		for (int i = 1; i < chatRoomList.length; i++) {
			listOfAllChatRooms[i - 1] = chatRoomList[i];
		}
		chatrooms.setListData(listOfAllChatRooms);
	}

	public String[] getListOfAllUsers() {
		return this.listOfAllUsers;
	}

	@Override
	public void displayInfo(String msg) {
		
	}
	
	public void createMainView(){
		frame.remove(currentPanel);
		frame.revalidate();
		currentPanel = makeChatMainScreen();
		tabbedPane = new JTabbedPane();
		currentPanel.setName("Home");
		tabbedPane.add(currentPanel);
		frame.setContentPane(tabbedPane);
		frame.setVisible(true);
	}
	
	public void updateLists(){
		client.onCommandEntered("list-all");
		client.onCommandEntered("list-all-chat-rooms");
	}
	
	public void updateChat(String msg){
		String[] array = msg.split(":");
		array[1] = array[1].trim();
		if(!tabMap.containsKey(array[1])){
			JPanel p = makeChatScreen(array[1]);
			tabMap.put(array[1], p);
			tabbedPane.add(p);
			tabbedPane.setSelectedComponent(p);
		}
		else{
			tabbedPane.setSelectedComponent(tabMap.get(array[1]));
		}
		JTextArea textArea = (JTextArea) (((JViewport) (((JScrollPane) tabMap.get(array[1]).getComponent(1)).getViewport()))).getView();
		textArea.append(array[2].trim() + "\n\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	@Override
	public void displayMessage(String msg) {
		if (msg.contains("Online Users:")) {
			this.setListOfAllUsers(msg);
		} 
		else if (msg.contains("Chat Rooms:")) {
			this.setListOfAllChatRooms(msg);
		}
		else if (msg.contains("Info: Welcome,")) {
			createMainView();
		}
		else if (msg.equals("update user list")) {
			this.updateLists();
		}
		else if (msg.equals("update user list")) {
			this.updateLists();
		}
		else if (msg.contains("Message:")) {
			this.updateChat(msg);
		}
		else if (msg.contains("ChatRoom:")) {
			this.updateChat(msg);
		}
		else {
			// chatArea.setText(chatArea.getText()+"\n"+msg);
		}
	}

}
