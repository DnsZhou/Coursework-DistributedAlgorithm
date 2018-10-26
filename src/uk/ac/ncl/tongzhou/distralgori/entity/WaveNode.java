package uk.ac.ncl.tongzhou.distralgori.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class WaveNode {
	private int id;
	protected LinkedList<Message> messageQueue;
	protected Map<WaveNode, Boolean> recp;
	protected WaveNode parent;
	protected List<WaveNode> children;
	protected StatusType status;
	protected WaveNode silentNeighbour;

	public WaveNode(int id) {
		this(id, null);
	}

	public WaveNode(int id, WaveNode parent) {
		this.id = id;
		this.parent = parent;
		this.children = new ArrayList<WaveNode>();
		this.recp = new HashMap<>();
		this.messageQueue = new LinkedList<>();
		this.status = StatusType.WAVE_START;
	}

	public int getId() {
		return id;
	}

	public WaveNode getParent() {
		return parent;
	}

	public List<WaveNode> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return "ServerNode [id=" + id + "]";
	}

	public boolean hasNode(WaveNode targetNode) {
		if (this == targetNode)
			return true;
		else
			for (WaveNode child : children) {
				if (child.hasNode(targetNode))
					return true;
			}
		return false;
	}

	public boolean hasChild() {
		return this.children != null && this.children.size() > 0;
	}

	public void addChildren(WaveNode child) {
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

	protected int getFalseNodeAmountInRecp() {
		return (int) this.recp.values().stream().filter(val -> !val).count();
	}

	protected WaveNode getFirstFalseNodesInRecp() {
		return this.recp.keySet().stream().filter(key -> (this.recp.get(key) == false)).findFirst().get();
	}

	/**
	 * @Title: readMessage
	 * @Description: Poll out one message from message queue and put it into
	 *               Recp
	 */
	private void readMessage() {
		Message msg = this.messageQueue.poll();
		if (this.recp.get(msg.getSender()) != null) {
			this.recp.put(msg.getSender(), true);
		} else {
			throw new IllegalStateException("Message Sender not found in Recp");
		}
	}

	private void display(StatusType lastStatus, StatusType newStatus, WaveNode relatedNode) {
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
		while (getFalseNodeAmountInRecp() >= 1 && this.messageQueue.size() > 0) {
			if (getFalseNodeAmountInRecp() == 1) {
				WaveNode falseNode = getFirstFalseNodesInRecp();
				this.silentNeighbour = falseNode;
			}
			readMessage();
		}
		if (getFalseNodeAmountInRecp() <= 1 && this.status == StatusType.WAVE_START) {
			this.silentNeighbour = this.silentNeighbour != null ? this.silentNeighbour
					: this.recp.keySet().stream().filter(key -> !this.recp.get(key)).findFirst().get();
			Message msg = new Message(this);
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
