package com.group8.gui;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField; 
 
class Buttons extends JPanel
{
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    
    private JButton loginButton = new JButton("Login");
    private JButton newUserButton = new JButton("New user ?");
    private JButton createButton = new JButton ("Create !");
    private JButton nvmButton = new JButton("Never Mind");
    
    private JButton addButton = new JButton("Add");
    private JButton editButton = new JButton("Edit");
    private JButton deleteButton = new JButton("Delete");
  
 
    public Buttons()
    {  
      panel.setLayout(new FlowLayout());
      panel.add(loginButton);
      panel.add(newUserButton);
      panel.add(createButton);
      panel.add(nvmButton);
      
      setLoginButtonVisibility(true);
      /*
      panel.add(addButton);
      panel.add(editButton);
      panel.add(deleteButton);
        */
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
    
    
    
    
    public void addButtonAddActionListener(ActionListener listener)
    {
      addButton.addActionListener(listener);
    }
     
    public void editButtonAddActionListener(ActionListener listener)
    {
      editButton.addActionListener(listener);
    }
    
    public void deleteButtonAddActionListener(ActionListener listener)
    {
      deleteButton.addActionListener(listener);
    }
       
   
}
 
 class Student extends JPanel
 {
 	boolean newUserButtonClicked;
    Buttons buttons = new Buttons();
    StudentInformation student = new StudentInformation();
    boardDisplay display = new boardDisplay();
    Panel0 panel0 = new Panel0();
    
    public Student()
    {

        JPanel panel = new JPanel();       
        panel.setLayout(new BorderLayout());
        newUserButtonClicked = false;

       
        
         panel.add (panel0, BorderLayout.NORTH);
         panel.add(buttons, BorderLayout.SOUTH);
        
         panel.add(student, BorderLayout.CENTER);
         /*
         panel.add(display, BorderLayout.SOUTH);
        */
        add(panel);
         
        buttons.loginButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

            	/*check ID, password and decide if a user can log in */
            	
            }
        });
        
        buttons.newUserButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	newUserButtonClicked = true;
            	
            	buttons.setLoginButtonVisibility(!newUserButtonClicked);
            	panel0.setLoginLabelVisibility(!newUserButtonClicked);
            	student.setConfirmPasswordVisibility(newUserButtonClicked);
            	
            	student.clearTextFields();
            	
            }
        });
        
        buttons.createButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

            	/* create a user */
            	
            }
        });
        
        buttons.nvmButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	newUserButtonClicked = false;
            	
            	buttons.setLoginButtonVisibility(!newUserButtonClicked);
            	panel0.setLoginLabelVisibility(!newUserButtonClicked);
            	student.setConfirmPasswordVisibility(newUserButtonClicked);
            	
            	student.clearTextFields();
            }
        });
        
        
        
        buttons.addButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String firstName = student.getName();
                String roomNumber = student.getRoomNumber();
                String terminalNumber = student.getTerminalNumber();
                String comment = student.getComment();
                 
                display.addRow(firstName, roomNumber, terminalNumber, comment);
            }
        });
         
        buttons.editButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                 String firstName = student.getName();
                 String roomNumber = student.getRoomNumber();
                 String terminalNumber = student.getTerminalNumber();
                 String comment = student.getComment();
                 
                 String[] Infos = { firstName, roomNumber, terminalNumber, comment };
                 display.editData(Infos);
                }
            });
        
        buttons.deleteButtonAddActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                   display.deleteRow();
                }
            });
    }
}
 
  class Panel0 extends JPanel {
	  /* basic labels */
	    private JLabel loginLabel;
	    private JLabel createAccountLabel;
	    
        JPanel panel = new JPanel();
        
	  public Panel0() {
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

  	  
  class StudentInformation extends JPanel
{
   
    JPanel panel = new JPanel();
    
    // Declare JLabels
    private JLabel label1 = new JLabel("asdof");
    private JLabel label2 = new JLabel("Room Number");
    private JLabel label3 = new JLabel("Terminal Number");
    private JLabel label4  = new JLabel("Comment");
    

    
    /* label for user id, pw, confirm pw textfield */
    private JLabel userIDLabel = new JLabel("User ID");
    private JLabel passwordLabel = new JLabel("Password");
    private JLabel confirmPasswordLabel = new JLabel("Retype Password");

    /* label for error msg */
    private JLabel errMsgLabel = new JLabel();
    
    /* text fields for user input */
    private JTextField userIDTF = new JTextField();
    private JTextField passwordTF = new JTextField();
    private JTextField confirmPasswordTF = new JTextField();
    
    // Declare JTextFields
    private JTextField text1 = new JTextField(10);
    private JTextField text2 = new JTextField(10);
    private JTextField text3 = new JTextField(10);
    private JTextField text4 = new JTextField(10);
     
     
    public StudentInformation()
    { 
         
        // Set the Grid Layout
        panel.setLayout( new GridLayout(3, 2)); // 5 ,2
         
        // Add items to grid
        panel.add(userIDLabel);
        panel.add(userIDTF);
        panel.add(passwordLabel);
        panel.add(passwordTF);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordTF);
        
        
        setConfirmPasswordVisibility(false);
         
         
        add(panel);     
    
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
