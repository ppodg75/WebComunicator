package atj.queue.producer;

import java.util.Optional;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;

import atj.communicator.CommunicatorMessageData;
import atj.communicator.IMessageDataCollector;
import atj.communicator.ISender;
import atj.conf.CommunicationConf;
import atj.event.MessageEvent;

public class QueueMessageEventProducer implements ISender {
	private JMSContext jmsContext;
	private JMSProducer jmsProducer;
	private Queue queue;

	public QueueMessageEventProducer() throws javax.jms.JMSException {
		ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
		jmsContext = connectionFactory.createContext();
		connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, CommunicationConf.CommunicationPort);
		jmsProducer = jmsContext.createProducer();
		queue = new Queue(CommunicationConf.PNAME_ATJ_COMMUNICATION_QUEUE); // "ATJQueue"
	}
	
	public void sendMessageEvent(CommunicatorMessageData message) {
		MessageEvent evt = MessageEvent.from(message);
		System.out.println("Sending message: "+message);
		jmsProducer.send(queue, evt.toString());
		System.out.println("sent");
	}
	
	@Override
	public void send(String name, String text) {
		String msgText = Optional.ofNullable(text).orElse("<Empty>");
		CommunicatorMessageData msg = new CommunicatorMessageData(name, msgText);
		sendMessageEvent(msg);
    }	
	
}
