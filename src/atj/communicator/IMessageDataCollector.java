package atj.communicator;

import java.util.List;

public interface IMessageDataCollector {
	
	public void addMessageData(CommunicatorMessageData message);
	public CommunicatorMessageData getLastMessage();
	public List<CommunicatorMessageData> getMessages();

}
