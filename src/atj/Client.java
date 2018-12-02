package atj;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import atj.communicator.CommunicatorMessageData;
import atj.communicator.CommunicatorService;
import atj.communicator.IMessageDataCollector;
import atj.communicator.IMessageNotifier;
import atj.queue.producer.QueueMessageEventProducer;
import atj.topic.publisher.TopicMessageEventPublisher;

public class Client implements IMessageDataCollector {

	private CommunicatorService communicatorService;
	private IMessageNotifier messageNotifier;
	private String name;

	private List<CommunicatorMessageData> messages = new ArrayList<>();

	public Client(String name, IMessageNotifier messageNotifier) {
		this.name = name;
		this.messageNotifier = messageNotifier;
		try {
			communicatorService = new CommunicatorService(this, false);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public Client(String name) {
		this(name, null);
	}
	
	public void send(String text) {
		System.out.println("Client send message >>>>> "+text);
		communicatorService.send(name, text);
	}

	@Override
	public List<CommunicatorMessageData> getMessages() {
		return messages;
	}

	@Override
	public CommunicatorMessageData getLastMessage() {
		return messages.size() == 0 ? null : messages.get(messages.size() - 1);
	}

	@Override
	public void addMessageData(CommunicatorMessageData message) {
		messages.add(message);		
		notifyNewMessage();
	}

	public void notifyNewMessage() {
		System.out.println("new message!");
		if (messageNotifier != null) {
			messageNotifier.newMessage();
		}
	}

}
