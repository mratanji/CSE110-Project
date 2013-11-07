package com.group8.client;

import java.util.List;

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

import org.apache.activemq.ActiveMQConnectionFactory;

import com.group8.view.ConsoleView;
import com.group8.view.View;

public class ChatClient implements MessageListener { 
    private MessageProducer producer;
    private Session session;
    private Destination tempDest;
    private View view;
    private String username;
    private CommandGroup commandGroup;
    
    public ChatClient() {
    	view = new ConsoleView(this);
    	commandGroup = new CommandGroup();
    	setupConnection();
    	displayWelcomeMessage();
    	displayHelp();
    }
    
    public void onCommandEntered(String message){
    	//This needs much better error checking.
		if(commandGroup.isValidCommand(message)){
			String[] commandComponents = message.split(":");
			if(commandComponents[0].equals("sign-on")){
				this.username = commandComponents[1];
			}
			else if(commandComponents[0].equals("exit")){
				System.exit(0);
			}
			send(message);
		}
		else{
			view.displayInfo("Invalid command");
		}
    }
    
    private void send(String message){
    	try{
            TextMessage txtMessage = session.createTextMessage();
            txtMessage.setText(message);
            txtMessage.setJMSReplyTo(tempDest);
            txtMessage.setStringProperty("username", this.username);
            this.producer.send(txtMessage);
    	}
    	catch(Exception e){
    		
    	}
    }
    
    //Message listener
    public void onMessage(Message message) {
        String messageText = null;
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                messageText = textMessage.getText();
                view.displayMessage(messageText + "\n");
            }
        } 
        catch (JMSException e) {

        }
    }
    
    private void displayWelcomeMessage(){
    	view.displayInfo("Welcome to Chat Roulette!\n");
    }
    
    private void displayHelp(){
    	view.displayInfo("Type one of the following commands: \n");
    	view.displayInfo("\t To add user type: \"add-user:<your_username>\" \n"
    			+ "\t To remove your user account type: \"remove-user:<your_username>\" \n"
    			+ "\t To sign on type: \"sign-on:<your_username>\" \n"
    			+ "\t To sign off type: \"sign-off:<your_username>\" \n"
    			+ "\t To send a message type: \"send:<user_you_are_sending_to>:<message>\" \n"
    			+ "\t To broadcast a message to all online users type: \"broadcast:<message>\" \n"
    			+ "\t To list all online users type: \"list-all:<your_username>\" \n");
    	view.displayInfo("Enter commands below:");
    }
    
    private void setupConnection(){
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.ACTIVEMQ_URL);
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination adminQueue = session.createQueue(Constants.QUEUENAME);
 
            //Setup a message producer to send message to the queue the server is consuming from
            this.producer = session.createProducer(adminQueue);
            this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 
            //Create a temporary queue that this client will listen for responses on then create a consumer
            //that consumes message from this temporary queue...for a real application a client should reuse
            //the same temp queue for each message to the server...one temp queue per client
            tempDest = session.createTemporaryQueue();
            MessageConsumer responseConsumer = session.createConsumer(tempDest);
 
            //This class will handle the messages to the temp queue as well
            responseConsumer.setMessageListener(this);
        } 
        catch (JMSException e) {

        }
    }
    
    public static void main(String[] args){
    	new ChatClient();
    }
}