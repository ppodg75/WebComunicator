package atj;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import atj.communicator.CommunicatorMessageData;
import atj.communicator.IMessageNotifier;

@ServerEndpoint("/endpoint")
public class ClientWebSocket implements IMessageNotifier {

	private int clientEpId = 0;

	private Client client;

	private static ConcurrentLinkedQueue<Session> peers = new ConcurrentLinkedQueue<>();

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("onOpen::" + session.getId());
		peers.add(session);
	}

	
	@OnClose
	public void onClose(Session session) {
		System.out.println("onClose::" + session.getId());
		peers.remove(session);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		// System.out.println("onMessage::From=" + session.getId() + " Message="
		// + message);
		client.send(message);
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("onError::" + t.getMessage());
	}

	public ClientWebSocket() {
		client = new Client("Piotr", this);
		Random generator = new Random();
		clientEpId = generator.nextInt();
	}

	public void newMessage() {
		if (peers.size() > 0) {
			{
				String msg = clientEpId + "#" + formatMsg(client.getLastMessage());
				peers.forEach(peer -> {
					send(peer, msg);
				});
			}
		}
	}

	public String formatMsg(CommunicatorMessageData msg) {
		return msg.getFormattedDateTime() + "#" + msg.getName() + "#" + msg.getMessage();
	}

	public void send(Session session, String msg) {
		try {
			System.out.println(
					"ClientWebSocker - sending message to session id: " + session.getId() + " >> message " + msg);
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
