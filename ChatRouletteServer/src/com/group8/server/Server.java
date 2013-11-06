package com.group8.server;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.group8.database.*;

import javax.jms.*;

public class Server implements MessageListener {
 
    private Session session;
    private MessageProducer producer;
    
    private HashMap<String, User> userMap;
 
    public Server() {
    	userMap = new HashMap<String, User>();
    	setupBrokerService();
        setupMessageQueueConsumer();
    }
    
    public void onMessage(Message message) {    	
        try {
        	TextMessage txtMsg = (TextMessage) message;
            String messageText = txtMsg.getText();
            String[] commandComponents = messageText.split(":");
            
            //Combining the sign-on and the create user steps. These should be separate
            if(commandComponents[0].equals("sign-on")){
            	userMap.put(commandComponents[1], new User(commandComponents[1], message.getJMSReplyTo(), true));
            }
            else if(commandComponents[0].equals("send")){
            	User toUser = userMap.get(commandComponents[1]);
            	send(toUser.getDestination(), message.getStringProperty("username") + ": " + commandComponents[2]);
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
