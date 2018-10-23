package uk.ac.ncl.TongZhou.DistrAlgori.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class ServerNode {
	private int id;
	private LinkedList<Message> messageQueue;
	private Map<ServerNode, Boolean> recp;
	private ServerNode parent;
	private List<ServerNode> children;
	private StatusType status;
	private ServerNode silentNeighbour;

	public ServerNode(int id) {
		this(id, null);
	}

	public ServerNode(int id, ServerNode parent) {
		this.id = id;
		this.parent = parent;
		this.children = new ArrayList<ServerNode>();
		this.recp = new HashMap<>();
		this.messageQueue = new LinkedList<>();
		this.status = StatusType.WAVE_START;
	}

	public int getId() {
		return id;
	}

	public ServerNode getParent() {
		return parent;
	}

	public List<ServerNode> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return "ServerNode [id=" + id + "]";
	}

	public boolean hasNode(ServerNode targetNode) {
		if (this == targetNode)
			return true;
		else
			for (ServerNode child : children) {
				if (child.hasNode(targetNode))
					return true;
			}
		return false;
	}

	public boolean hasChild() {
		return this.children != null && this.children.size() > 0;
	}

	public void addChildren(ServerNode child) {
		this.children.add(child);
		child.parent = this;
	}

	public void initializeRecp() {
		if (this.parent != null)
			this.recp.put(parent, false);
		children.forEach(child -> this.recp.put(child, false));
	}

	public boolean addMessage(Message newMsg) {
		if (newMsg == null || messageQueue.stream().anyMatch(msg -> (msg != null && msg.equals(newMsg))))
			return false;
		else {
			this.messageQueue.add(newMsg);
			return true;
		}
	}

	private int getFalseNodeAmountInRecp() {
		return (int) this.recp.values().stream().filter(val -> !val).count();
	}

	/**
	 * @Title: readMessage
	 * @Description: Poll out one message from message queue and put it into Recp
	 */
	private void readMessage() {
		Message msg = this.messageQueue.poll();
		if (this.recp.get(msg.getSender()) != null) {
			this.recp.put(msg.getSender(), true);
		} else {
			throw new IllegalStateException("Message Sender not found in Recp");
		}
	}

	private void display(StatusType lastStatus, StatusType newStatus, ServerNode relatedNode) {
		if (lastStatus == newStatus)
			System.out.println(this.toString() + ": Do nothing");
		else if (lastStatus == StatusType.WAVE_START && newStatus == StatusType.WAVE_SENT_TO_SILENT_NEIGHBOUR)
			System.out.println(this.toString() + ": Send a token to its silent neighbour: " + relatedNode.toString());
		else if (lastStatus == StatusType.WAVE_SENT_TO_SILENT_NEIGHBOUR && newStatus == StatusType.WAVE_DECIDE) {
			System.out.println(
					this.toString() + ": has recived a token from its silent neighbour: " + relatedNode.toString());
			System.out.println(this.toString() + ": Decides!");
		}
	}

	public void activate() {
		StatusType lastStatus = this.status;
		while (getFalseNodeAmountInRecp() > 1 && this.messageQueue.size() > 0) {
			readMessage();
		}
		if (getFalseNodeAmountInRecp() <= 1 && this.status == StatusType.WAVE_START) {
			this.silentNeighbour = this.recp.keySet().stream().filter(key -> !this.recp.get(key)).findFirst().get();
			Message msg = new Message(this);
			// silentNeighbour.addMessage(msg);
			if (!this.silentNeighbour.addMessage(msg))
				throw new IllegalStateException("failed to add message or duplicate message sent to same node");
			this.status = StatusType.WAVE_SENT_TO_SILENT_NEIGHBOUR;
			display(StatusType.WAVE_START, StatusType.WAVE_SENT_TO_SILENT_NEIGHBOUR, this.silentNeighbour);
			if (this.messageQueue.size() > 0)
				readMessage();
		}
		if (getFalseNodeAmountInRecp() == 0 && this.status == StatusType.WAVE_SENT_TO_SILENT_NEIGHBOUR) {
			this.status = StatusType.WAVE_DECIDE;
			display(StatusType.WAVE_SENT_TO_SILENT_NEIGHBOUR, StatusType.WAVE_DECIDE, this.silentNeighbour);
		}
		if (lastStatus == this.status)
			display(lastStatus, this.status, null);

	}
}
