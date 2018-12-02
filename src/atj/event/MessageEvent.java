package atj.event;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import atj.communicator.CommunicatorMessageData;

public class MessageEvent {
	
	private static final String sep = "#";
	private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
	private String message;
	
	public MessageEvent(String message) {
		this.message = message;
	}
	
	public static MessageEvent from(CommunicatorMessageData message) {
		return new MessageEvent(getFormattedDateTime(message.getDateTime()) + sep + message.getName() + sep + message.getMessage());
	}
	
	public static CommunicatorMessageData toMessage(MessageEvent event) {		
		String[] data = event.message.split(sep);
		String dateTime = data[0];
		String name = data[1];
		String message = data[2];
		
		for(int i=3; i <= data.length-1;  i++ ) { message = message.concat(data[i]); }
		
		CommunicatorMessageData msg = new CommunicatorMessageData(name, message, toDateTime(dateTime));
		
		return msg;
	}
	
	private static String getFormattedDateTime(LocalDateTime dateTime) {
		return dateTime.format(formatter);
	}
	
	private static LocalDateTime toDateTime(String formatedDateTime) {
	 return	LocalDateTime.parse(formatedDateTime, formatter);
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	

}
