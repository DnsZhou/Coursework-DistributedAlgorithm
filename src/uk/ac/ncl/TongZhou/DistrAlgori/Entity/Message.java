package uk.ac.ncl.TongZhou.DistrAlgori.Entity;
/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 22 Oct 2018 12:53:36
 */
public class Message {
	private ServerNode fromNode;
	private ServerNode toNode;
	
	/**
	 *the comparing Id for the election algorithm 
	 */
	private Integer rId;
	
	/**
	 *message types:
	 *1,wakeup message 
	 *2,election message
	 *3,decision message
	 */
	private int messagetype;
	
	
	public Message(ServerNode fromNode, ServerNode toNode, Integer rId, int messagetype) {
		super();
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.rId = rId;
		this.messagetype = messagetype;
	}
	
	public Message(ServerNode fromNode, ServerNode toNode) {
		this(fromNode, toNode, null, 1);
	}
	public ServerNode getFromNode() {
		return fromNode;
	}
	public ServerNode getToNode() {
		return toNode;
	}
	
}
