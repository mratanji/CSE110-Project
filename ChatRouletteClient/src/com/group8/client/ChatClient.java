

package com.group8.client;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class ChatClient {
	private MessageProducer producer;
	private Session session;
	
	public ChatClient(MessageProducer producer, Session session) {
		super();
		this.producer = producer;
		this.session = session;
	} 
	
	public void send(String msg) throws JMSException {
		producer.send(session.createTextMessage(msg));
	}
}
