package com.group8.client;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.group8.view.*;

import javax.jms.*;

public class ChatClient implements MessageListener { 
    private MessageProducer producer;
    private Session session;
    private Destination tempDest;
    private View view;
    private boolean inputIsUsername = true;
    private String username;
    
 
    public ChatClient() {
    	view = new ConsoleView(this);
    	setupConnection();
    	displayWelcomeMessage();
    	displayHelp();
    }
    
    public void onCommandEntered(String message){
    	//Parse the command entered.
    	if(inputIsUsername){
    		this.username = message;
    		inputIsUsername = false;
    		send("sign-on:" + this.username);
    	}
    	else{
    		//Need a more extensive parse here, for now we are assuming command: send message to specified user
    		String[] commandComponents = message.split(":");
    		if(commandComponents.length == 3){
    			send(message);
    		}
    		else{
    			view.displayInfo("Invalid command");
    		}
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
    	view.displayInfo("Information about the commands available will go here. //TODO.\n");
    	view.displayInfo("Please enter username: ");
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