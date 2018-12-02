package atj.topic.subscriber;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Topic;

import atj.communicator.IMessageReceiver;
import atj.conf.CommunicationConf;
import atj.consumer.MessageEventConsumer;

public class TopicMessageEventSubscriber {
	
	private JMSContext jmsContext;
	private JMSConsumer jmsConsumer;
	private Topic topic;
	
	private MessageEventConsumer messageEventConsumer;
	
	public TopicMessageEventSubscriber(IMessageReceiver messageReceiver) throws JMSException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		jmsContext = connectionFactory.createContext();
		connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, CommunicationConf.CommunicationPort);
		topic = new Topic(CommunicationConf.PNAME_ATJ_COMMUNICATION_TOPIC); // "ATJQueue"
		jmsConsumer = jmsContext.createConsumer(topic);
		messageEventConsumer = new MessageEventConsumer(messageReceiver);
		jmsConsumer.setMessageListener(messageEventConsumer);
	}
	
	
}
