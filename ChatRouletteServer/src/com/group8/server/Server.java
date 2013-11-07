package com.group8.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.group8.database.*;

import javax.jms.*;

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
            	try {
					userDatabase.addUser(username, password, userDestination);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR!: Log file couldn't be instantiated!");
					e.printStackTrace();
				}
            }
            else if(commandComponents[0].equals("remove-user")){
            	String username = commandComponents[1];
            	try {
					userDatabase.removeUser(username);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR!: Log file couldn't be instantiated!");
					e.printStackTrace();
				}
            }
            else if(commandComponents[0].equals("sign-on")){
            	String username = commandComponents[1];
            	Destination userDestination = message.getJMSReplyTo();
            	userDatabase.signOnUser(username, userDestination);
            }
            else if(commandComponents[0].equals("sign-off")){
            	String username = commandComponents[1];
            	userDatabase.signOffUser(username);
            }
            else if(commandComponents[0].equals("send")){
            	String toUsername = commandComponents[1];
            	String newMessage = message.getStringProperty("username") + ": " + commandComponents[2];
            	send(userDatabase.getUserDestination(toUsername), newMessage);
            }
            else if(commandComponents[0].equals("broadcast")){
            	//For each user, send this message
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
