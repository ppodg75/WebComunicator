package atj.communicator;

import javax.jms.JMSException;

import atj.event.MessageEvent;
import atj.queue.consumer.QueueConsumerService;
import atj.queue.producer.QueueMessageEventProducer;
import atj.topic.publisher.TopicMessageEventPublisher;
import atj.topic.subscriber.TopicMessageEventSubscriber;

public class CommunicatorService implements IMessageReceiver {
	
	private QueueConsumerService queueConsumerService;
	private TopicMessageEventSubscriber topicMessageEventSubscriber;
	private ISender sender;	
	private IMessageDataCollector messageCollector;
	private boolean runAsServer;

	public CommunicatorService (IMessageDataCollector messageCollector, boolean runAsServer) throws JMSException {		
		this.messageCollector=messageCollector;
		if (runAsServer) {
			this.queueConsumerService = new QueueConsumerService(this);
    		this.sender = new TopicMessageEventPublisher();			
		} else {
			this.topicMessageEventSubscriber = new TopicMessageEventSubscriber(this);
			this.sender = new QueueMessageEventProducer();
		}		
	}
	
	public void send(String name, String text) {
		sender.send(name, text);
	}
	
	public void receive(MessageEvent evt) {
		CommunicatorMessageData msg = MessageEvent.toMessage(evt);
		addMessageData(msg);
	}
	
	
	public void addMessageData(CommunicatorMessageData msg) {
		messageCollector.addMessageData(msg);
	}
}
