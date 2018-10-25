
/**
 * @Description 
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 03:08 23-10-2018
 */
package uk.ac.ncl.tongzhou.distralgori.entity;

/**
 * @ClassName Message
 * @Description
 * 
 */
public class Message {
	private ServerNode sender;
	private MessageType messagetype;
	private ServerNode electionNode;

	/**
	 * @Title Constructor for Message
	 * @Description
	 * @param sender
	 * @param messagetype
	 * @param electionNode
	 */
	private Message(ServerNode sender, MessageType messagetype, ServerNode electionNode) {
		super();
		this.sender = sender;
		this.messagetype = messagetype;
		this.electionNode = electionNode;
	}

	/**
	 * @Title Constructor for WAVE_MESSAGE
	 * @Description
	 * @param sender
	 */
	public Message(ServerNode sender) {
		this(sender, MessageType.WAVE_MESSAGE, null);
	}
	
	
	/**   
	 * @Title Constructor for ELECTION_WAKEUP_MESSAGE  
	 * @Description 
	 * @param sender
	 * @param messageType 
	 */  
	public Message(ServerNode sender, MessageType messageType) {
		this(sender, messageType, null);
	}

	/**
	 * @Title Constructor for ELECTION_TOKEN_MESSAGE
	 * @Description
	 * @param sender
	 * @param electionNode
	 */
	public Message(ServerNode sender, ServerNode electionNode) {
		this(sender, MessageType.ELECTION_TOKEN_MESSAGE, electionNode);
	}

	/**
	 * @Title toString
	 * @Description
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Message [sender=" + sender + ", messagetype=" + messagetype + ", electionNode=" + electionNode + "]";
	}

	// Overriding equals() to compare two Message objects
	@Override
	public boolean equals(Object o) {

		// If the object is compared with itself then return true
		if (o == this) {
			return true;
		}

		/*
		 * Check if o is an instance of Message or not "null instanceof [type]" also
		 * returns false
		 */
		if (!(o instanceof Message)) {
			return false;
		}

		// typecast o to Message so that we can compare data members
		Message msg = (Message) o;

		// Compare the data members and return accordingly
		return this.sender == msg.sender && this.electionNode == msg.electionNode
				&& this.messagetype == msg.messagetype;
	}

	
	/** 
	 * Return the sender.
	 *
	 * @return sender 
	 */
	public ServerNode getSender() {
		return sender;
	}

	
	/** 
	 * Return the messagetype.
	 *
	 * @return messagetype 
	 */
	public MessageType getMessagetype() {
		return messagetype;
	}

	
	/** 
	 * Return the electionNode.
	 *
	 * @return electionNode 
	 */
	public ServerNode getElectionNode() {
		return electionNode;
	}
	
	

}
