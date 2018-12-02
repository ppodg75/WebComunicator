package atj.queue.consumer;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;

import atj.communicator.IMessageReceiver;
import atj.conf.CommunicationConf;
import atj.consumer.MessageEventConsumer;

public class QueueConsumerService {

	private JMSContext jmsContext;
	private JMSConsumer jmsConsumer;
	private Queue queue;

	private MessageEventConsumer messageEventConsumer;

	public QueueConsumerService(IMessageReceiver messageReceiver) throws JMSException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		jmsContext = connectionFactory.createContext();
		connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, CommunicationConf.CommunicationPort);
		queue = new Queue(CommunicationConf.PNAME_ATJ_COMMUNICATION_QUEUE); // "ATJQueue"
		jmsConsumer = jmsContext.createConsumer(queue);
		messageEventConsumer = new MessageEventConsumer(messageReceiver);
		jmsConsumer.setMessageListener(messageEventConsumer);
	}

}
