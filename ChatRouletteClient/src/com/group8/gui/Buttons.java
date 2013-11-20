package com.group8.gui;

import java.awt.FlowLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import com.group8.client.ChatClient;
 
class Buttons extends JPanel
{
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    
    private JButton loginButton = new JButton("Login");
    private JButton newUserButton = new JButton("New user ?");
    private JButton createButton = new JButton ("Create !");
    private JButton nvmButton = new JButton("Never Mind");
  
 
    public Buttons()
    {  
      panel.setLayout(new FlowLayout());
      panel.add(loginButton);
      panel.add(newUserButton);
      panel.add(createButton);
      panel.add(nvmButton);
      setLoginButtonVisibility(true);
      add(panel);
    }
 
	public void setLoginButtonVisibility(boolean visible) {
		loginButton.setVisible(visible);
		newUserButton.setVisible(visible);
		createButton.setVisible(!visible);
		nvmButton.setVisible(!visible);
	}
    

    public void loginButtonAddActionListener(ActionListener listener)
    {
      loginButton.addActionListener(listener);
    }
    
    public void newUserButtonAddActionListener(ActionListener listener)
    {
      newUserButton.addActionListener(listener);
    }
    
    public void createButtonAddActionListener(ActionListener listener)
    {
      createButton.addActionListener(listener);
    }
    
    public void nvmButtonAddActionListener(ActionListener listener)
    {
      nvmButton.addActionListener(listener);
    }
       
   
}


 
 class Login extends JPanel
 {
	 
	 //Client variable: 
	 ChatClient client; 
	 
 	boolean newUserButtonClicked;
    Buttons buttons = new Buttons();
    LoginInformation loginInfo = new LoginInformation();
    boardDisplay display = new boardDisplay();
    TitlePanel title = new TitlePanel();
    
    public Login(ChatClient c)
    {
    	//INITIALIZE: Chat client object with current view
    	client = c; 
    	
        final JPanel panel = new JPanel(); 
        panel.setLayout(new BorderLayout());
     
        newUserButtonClicked = false;

       
        
         panel.add(title, BorderLayout.NORTH);
         panel.add(buttons, BorderLayout.SOUTH);
        
         panel.add(loginInfo, BorderLayout.CENTER);

        add(panel);
         
        final Dimension size = new Dimension(600, 700); 
        
        buttons.loginButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	String commandMessage = loginInfo.signOnUser(); 
            	client.onCommandEntered(commandMessage); 
            	new ChatDisplay(client); 
            	JFrame jf = new JFrame();
            	jf.setMinimumSize(size);  
            	jf.add(new ChatDisplay(client)); 
            	jf.setVisible(true); 
            }
        });
        
        buttons.newUserButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	newUserButtonClicked = true;
            	
            	buttons.setLoginButtonVisibility(!newUserButtonClicked);
            	title.setLoginLabelVisibility(!newUserButtonClicked);
            	loginInfo.setConfirmPasswordVisibility(newUserButtonClicked);
            	
            	loginInfo.clearTextFields();
            	
            }
        });
        
        buttons.createButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

            	if(loginInfo.passwordCreateMatch())
            	{
                	/* create a user */
                	String commandMessage = loginInfo.createNewUser(); 
                	client.onCommandEntered(commandMessage); 
            	}
            }
        });
        
        buttons.nvmButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	newUserButtonClicked = false;
            	
            	buttons.setLoginButtonVisibility(!newUserButtonClicked);
            	title.setLoginLabelVisibility(!newUserButtonClicked);
            	loginInfo.setConfirmPasswordVisibility(newUserButtonClicked);
            	
            	loginInfo.clearTextFields();
            }
        });
        
        
      
    }
}
 
  class TitlePanel extends JPanel {
	  /* basic labels */
	    private JLabel loginLabel;
	    private JLabel createAccountLabel;
	    
        JPanel panel = new JPanel();
        
	  public TitlePanel() {
		  loginLabel = new JLabel("Login");
		  createAccountLabel = new JLabel("Create a new account");
		
	        // Add items to grid
	        panel.add(loginLabel);
	        panel.add(createAccountLabel);
	        
	        setLoginLabelVisibility(true);
	        
	        add(panel);
	  }
	  
	  public void setLoginLabelVisibility(boolean visible) {
		  loginLabel.setVisible(visible);
		  createAccountLabel.setVisible(!visible);
	  }

  }

  	  
  class LoginInformation extends JPanel
{
	JPanel mainPanel = new JPanel();
    JPanel panel = new JPanel();

    
    /* label for user id, pw, confirm pw textfield */
    private JLabel userIDLabel = new JLabel("User ID");
    private JLabel passwordLabel = new JLabel("Password");
    private JLabel confirmPasswordLabel = new JLabel("Retype Password");
    
    /* label for error msg */
    private JLabel errMsgLabel = new JLabel();
    
    /* text fields for user input */
    private JTextField userIDTF = new JTextField();
    private JPasswordField passwordTF = new JPasswordField();
    private JPasswordField confirmPasswordTF = new JPasswordField();
    
    // Declare JTextFields
    private JTextField text1 = new JTextField(10);
    private JTextField text2 = new JTextField(10);
    private JTextField text3 = new JTextField(10);
    private JTextField text4 = new JTextField(10);
     
     
    public LoginInformation()
    { 
         
        // Set the Grid Layout
        panel.setLayout( new GridLayout(3, 2));
         
        // Add items to grid
        panel.add(userIDLabel);
        panel.add(userIDTF);
        panel.add(passwordLabel);
        panel.add(passwordTF);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordTF);
        

        setConfirmPasswordVisibility(false);
         
        mainPanel.add(panel, BorderLayout.NORTH);
        
        add(mainPanel);
    }
     
	public void setConfirmPasswordVisibility(boolean visible) {
		confirmPasswordLabel.setVisible(visible);
		confirmPasswordTF.setVisible(visible);
	}
	
	
	public void clearTextFields() {
		userIDTF.setText("");
		passwordTF.setText("");
		confirmPasswordTF.setText("");
	}
	  
	public boolean passwordCreateMatch(){
		boolean matches = true;
		char[] password = passwordTF.getPassword();
		char[] confirmPassword = confirmPasswordTF.getPassword();
		
		if (!Arrays.equals(password, confirmPassword)) {
			matches = false;
			
			JOptionPane.showMessageDialog(mainPanel, "Your password and the password confirmation do not match.");
		}
		
		else {
			matches = true;
			JOptionPane.showMessageDialog(mainPanel, "Your User ID: " + userIDTF.getText());
		}
			
		
		return matches;
	}
	
	public String createNewUser()
	{
		String s = new String("add-user:" + userIDTF.getText() + ":" + passwordTF.getText()); 
		return s; 
	}
	public String signOnUser()
	{
		String s = new String("sign-on:" + userIDTF.getText() + ":" + passwordTF.getText()); 
		return s; 
	}
	
    public String getName()
    {
        return text1.getText();
    }
     
    public String getRoomNumber()
    {
        return text2.getText();
    }
     
    public String getTerminalNumber()
    {
        return text3.getText();
    }
     
     public String getComment()
    {
        return text4.getText();
    }
     
     
     
}

