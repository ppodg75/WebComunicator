package atj.communicator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommunicatorMessageData {
	
	private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
	
	private String name;
	private String message;	
	private LocalDateTime dateTime;

	public CommunicatorMessageData(String name, String message) {
		this.dateTime = LocalDateTime.now();
		this.name=name;
		this.message = message;
	}
	
	public CommunicatorMessageData(String name, String message, LocalDateTime dateTime) {
		this.dateTime = dateTime;
		this.name=name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	@Override
	public String toString() {
		return getFormattedDateTime() + " " + name + "> " +message; 
	}
	
	public String getFormattedDateTime() {
		return formatter.format(dateTime);
	}
	
}
