package atj.communicator;

import atj.event.MessageEvent;

public interface IMessageReceiver {
	
	void receive(MessageEvent event);

}
