package com.group8.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.group8.database.UserDatabase;

public class Server implements MessageListener {
 
    private Session session;
    private MessageProducer producer;
    
    private UserDatabase userDatabase;
 
    public Server() {
    	userDatabase = new UserDatabase();
    	setupBrokerService();
        setupMessageQueueConsumer();
    }
    
    public void onMessage(Message message) {    	
        try {
        	TextMessage txtMsg = (TextMessage) message;
            String messageText = txtMsg.getText();
            String[] commandComponents = messageText.split(":");
            
            //Need to handle if the database returns false meaning our command did not execute.
            if(commandComponents[0].equals("add-user")){
            	String username = commandComponents[1];
            	String password = commandComponents[2];
            	Destination userDestination = message.getJMSReplyTo();
            	
				if(userDatabase.addUser(username, password, userDestination)){
					send(message.getJMSReplyTo(), "Info: '" + username + "' has been added to the database.");
				}
				else{
					send(message.getJMSReplyTo(), "Info: '" + username + "' is already in the database.");
				}
		
            }
            else if(commandComponents[0].equals("delete-my-account")){
            	String username = message.getStringProperty("username");
            	if(userDatabase.removeUser(username)){
            		send(message.getJMSReplyTo(), "You're account has been removed. We are sorry to see you go.");
            	}
            	else{
            		send(message.getJMSReplyTo(), "Info: Error deleting account. Are you signed on?");
            	}
            }
            else if(commandComponents[0].equals("sign-on")){
            	String username = commandComponents[1];
            	String password = commandComponents[2];
            	Destination userDestination = message.getJMSReplyTo();
            	if(userDatabase.signOnUser(username, password, userDestination)){
            		send(message.getJMSReplyTo(), "Info: Welcome, " + username + ".");
            	}
            	else{
            		send(message.getJMSReplyTo(), "Info: Incorrect username/password combination.");
            	}
            }
            else if(commandComponents[0].equals("sign-off")){
            	String username = message.getStringProperty("username");
            	if(userDatabase.signOffUser(username)){
            		send(message.getJMSReplyTo(), "Info: '" + username + "' has been signed off successfully.");
            	}
            	else{
            		send(message.getJMSReplyTo(), "Info: An error has occurred while trying to sign off.");
            	}
            }
            else if(commandComponents[0].equals("send")){
            	String toUsername = commandComponents[1];
            	String newMessage = message.getStringProperty("username") + ": " + commandComponents[2];
            	try{
            		send(userDatabase.getUserDestination(toUsername), newMessage);
            	}
            	catch(IllegalArgumentException e){
            		send(message.getJMSReplyTo(), "Info: '" + toUsername + "' is not online or is not a valid user.");
            	}
            }
            else if(commandComponents[0].equals("broadcast")){
            	//For each user, send this message
            	String broadcastMessage = message.getStringProperty("username") + ": " + commandComponents[1]; 
            	String[] usersOnline = userDatabase.getAllUsers(); 
            	for(String currentUser:usersOnline){
            		if(!currentUser.equals(message.getStringProperty("username"))){
                		send(userDatabase.getUserDestination(currentUser), broadcastMessage);
            		}
            	}
            }
            else if(commandComponents[0].equals("list-all")){
            	send(message.getJMSReplyTo(), userDatabase.listAllUsers()); 
            }
        }
        catch (JMSException e) {

        }
    }
    
    private void send(Destination destination, String message){
    	try{
            TextMessage txtMessage = session.createTextMessage();
            txtMessage.setText(message);
            this.producer.send(destination, txtMessage);
    	}
    	catch(Exception e){
    		
    	}
    }
    
    private void setupBrokerService(){
    	try {
            //This message broker is embedded
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector(Constants.ACTIVEMQ_URL);
            broker.start();
        } 
        catch (Exception e) {

        }
    }
 
    private void setupMessageQueueConsumer() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.ACTIVEMQ_URL);
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination adminQueue = this.session.createQueue(Constants.QUEUENAME);
 
            //Setup a message producer to respond to messages from clients, we will get the destination
            //to send to from the JMSReplyTo header field from a Message
            this.producer = this.session.createProducer(null);
            this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 
            //Set up a consumer to consume messages off of the admin queue
            MessageConsumer consumer = this.session.createConsumer(adminQueue);
            consumer.setMessageListener(this);
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
    }
 
    public static void main(String[] args){
    	new Server();
    }
}
