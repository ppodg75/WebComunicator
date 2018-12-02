package atj.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import atj.communicator.CommunicatorService;
import atj.communicator.IMessageReceiver;
import atj.event.MessageEvent;

public class MessageEventConsumer implements MessageListener {

	private final IMessageReceiver receiver;

	public MessageEventConsumer(IMessageReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage)
			try {
				String msg = ((TextMessage) message).getText();	
				System.out.println("Message received");
				receiver.receive(new MessageEvent(msg));
			} catch (JMSException e) {
				e.printStackTrace();
			}
	}

}
