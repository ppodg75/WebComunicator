package atj.topic.publisher;


import java.util.Optional;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Topic;

import com.sun.messaging.ConnectionConfiguration;

import atj.communicator.CommunicatorMessageData;
import atj.communicator.IMessageDataCollector;
import atj.communicator.ISender;
import atj.conf.CommunicationConf;
import atj.event.MessageEvent;

public class TopicMessageEventPublisher implements ISender {
	
	Topic topic;
	JMSProducer jmsProducer;
	JMSContext jmsContext;	

	public TopicMessageEventPublisher() throws JMSException {		
		ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
		((com.sun.messaging.ConnectionFactory)connectionFactory).setProperty(ConnectionConfiguration.imqAddressList, CommunicationConf.CommunicationPort);
		jmsContext = connectionFactory.createContext();
		topic = new com.sun.messaging.Topic(CommunicationConf.PNAME_ATJ_COMMUNICATION_TOPIC);		
	}
	
	public void sendMessage(CommunicatorMessageData message) {
		MessageEvent evt = MessageEvent.from(message);		
		try {
			System.out.println("Publishinh message: "+message);
			jmsProducer = jmsContext.createProducer();
			jmsProducer.send(topic, evt.toString());
			System.out.println("Published");
		} finally {
		  jmsContext.close();
		}
	}
	
	@Override
	public void send(String name, String text) {
		String msgText = Optional.ofNullable(text).orElse("<Empty>");
		CommunicatorMessageData msg = new CommunicatorMessageData(name, msgText);
		sendMessage(msg);		
	}
		
}