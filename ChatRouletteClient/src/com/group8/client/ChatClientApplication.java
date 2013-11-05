package com.group8.client;

import java.net.URISyntaxException;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;

public class ChatClientApplication {
	
	public static void main(String[] args) {
		try {			
			//Create the client and wire it to the communication platform
			ChatClient client = wireClient();
		} 
		catch (JMSException e) {
			e.printStackTrace();
		} 
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This inner class is used to make sure we clean up when the client closes
	 */
	static private class CloseHook extends Thread {
		ActiveMQConnection connection;
		private CloseHook(ActiveMQConnection connection) {
			this.connection = connection;
		}

		public static Thread registerCloseHook(ActiveMQConnection connection) {
			Thread ret = new CloseHook(connection);
			Runtime.getRuntime().addShutdownHook(ret);
			return ret;
		}

		public void run() {
			try {
				System.out.println("Closing ActiveMQ connection");
				connection.close();
			} catch (JMSException e) {
				/* 
				 * This means that the connection was already closed or got 
				 * some error while closing. Given that we are closing the
				 * client we can safely ignore this.
				 */
			}
		}
	}

	/*
	 * This method wires the client class to the messaging platform
	 * Notice that ChatClient does not depend on ActiveMQ (the concrete 
	 * communication platform we use) but just in the standard JMS interface.
	 */
	private static ChatClient wireClient() throws JMSException, URISyntaxException {
		ActiveMQConnection connection = ActiveMQConnection.makeConnection(
						/*Constants.USERNAME, Constants.PASSWORD,*/ Constants.ACTIVEMQ_URL);
		connection.start();
		CloseHook.registerCloseHook(connection);
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue destQueue = session.createQueue(Constants.QUEUENAME);
		MessageProducer producer = session.createProducer(destQueue);
		return new ChatClient(producer,session);
	}
}
