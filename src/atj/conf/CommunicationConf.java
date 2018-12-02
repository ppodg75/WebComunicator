package atj.conf;

public abstract class CommunicationConf {
	
	public static final String JNDI_ATJ_COMMUNICATION_QUEUE = "jms/ATJCommunicationQueue";
	public static final String PNAME_ATJ_COMMUNICATION_QUEUE = "ATJCommunicationQueue";
	
	public static final String JNDI_ATJ_COMMUNICATION_TOPIC = "jms/ATJCommunicationTopic";
	public static final String PNAME_ATJ_COMMUNICATION_TOPIC = "ATJCommunicationTopic";
	
	public static final String CommunicationPort= "localhost:7676/jms";

	public CommunicationConf() {
		// TODO Auto-generated constructor stub
	}

}
