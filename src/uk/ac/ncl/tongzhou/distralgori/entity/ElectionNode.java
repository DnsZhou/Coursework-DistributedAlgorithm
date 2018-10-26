
/**
 * @Description  
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 17:48 25-10-2018
 */
package uk.ac.ncl.tongzhou.distralgori.entity;

/**
 * @ClassName ElectionNode
 * @Description
 * 
 */
public class ElectionNode extends WaveNode {

	/**
	 * @Fields recivedWakeupCalls : WRp
	 */
	private int recivedWakeupCalls;

	/**
	 * @Fields winNode : Vp
	 */
	private WaveNode winNode;

	/**
	 * @Title Constructor for ElectionNode
	 * @Description
	 * @param id
	 */
	public ElectionNode(int id) {
		super(id);
		this.status = StatusType.ELECTION_SLEEP;
		this.winNode = this;
	}

	public ElectionNode(int id, WaveNode parent) {
		super(id, parent);
		this.status = StatusType.ELECTION_SLEEP;
		this.winNode = this;
	}

	@Override
	public void activate() {
		this.activate(false);
	}

	public void activate(boolean isInitiator) {
		StatusType lastStatus = this.status;
		if (isInitiator) {
			System.out.println(this.toString() + " is a initiator.");
			lastStatus = this.status;
			this.status = StatusType.ELECTION_WAKEUP;
			Message msg = new Message(this, MessageType.ELECTION_WAKEUP_MESSAGE);
			this.recp.keySet().forEach(node -> node.addMessage(msg));
			System.out.println(this.toString() + ": Send wake up message to all his neighbours.");
		}
		if (this.recivedWakeupCalls < this.recp.size()) {
			while (this.messageQueue.size() > 0 && this.hasWakeupMessage()) {
				this.readMessage(MessageType.ELECTION_WAKEUP_MESSAGE);
				if (this.status == StatusType.ELECTION_SLEEP) {
					lastStatus = this.status;
					this.status = StatusType.ELECTION_WAKEUP;
					System.out.println(this.toString() + ": Wake up.");
					Message msg = new Message(this, MessageType.ELECTION_WAKEUP_MESSAGE);
					this.recp.keySet().forEach(node -> node.addMessage(msg));
					System.out.println(this.toString() + ": Send wake up message to all his neighbours.");
				}
			}
		}
		if (this.recivedWakeupCalls == this.recp.size()) {
			// Now start the tree algorithm
			while (this.getFalseNodeAmountInRecp() > 1 && this.hasTokenMessage()) {
				this.readMessage(MessageType.ELECTION_TOKEN_MESSAGE);
			}
			if (this.getFalseNodeAmountInRecp() == 1) {
				if (this.status == StatusType.ELECTION_WAKEUP) {
					Message msg = new Message(this, this.winNode);
					this.silentNeighbour = this.silentNeighbour != null ? this.silentNeighbour
							: this.getFirstFalseNodesInRecp();
					this.silentNeighbour.addMessage(msg);
					lastStatus = this.status;
					this.status = StatusType.ELECTION_SENT_TO_SILENT_NEIGHBOUR;

					// Token message include the Vp in current stage.
					System.out.println(this.toString() + ": Send token message to its silent neighbour.");
				}
				if (this.readMessage()) {
					if (this.winNode == this) {
						lastStatus = this.status;
						this.status = StatusType.ELECTION_LEADER;
						System.out.println(this.toString() + " decides and be the LEADER");
					} else {
						lastStatus = this.status;
						this.status = StatusType.ELECTION_LOST;
						System.out.println(this.toString() + " decides and LOST");
					}
					Message decideMessage = new Message(this, this.winNode);
					this.recp.keySet().stream().filter(node -> node != this.silentNeighbour)
							.forEach(node -> node.addMessage(decideMessage));
					if (this.recp.size() > 1)
						System.out.println(this.toString()
								+ ": send decide message to all its neighbours other than its silent neighbour.");
				}
			}
		}
		if (lastStatus == this.status)
			System.out.println(this.toString() + ": Do nothing.");
	}

	private boolean readMessage() {
		if (this.messageQueue.size() > 0) {
			Message msg = this.messageQueue.poll();
			if (msg.getMessagetype() == MessageType.ELECTION_WAKEUP_MESSAGE)
				this.recivedWakeupCalls += 1;
			else if (msg.getMessagetype() == MessageType.ELECTION_TOKEN_MESSAGE) {
				if (this.recp.get(msg.getSender()) != null) {
					if (this.getFalseNodeAmountInRecp() == 1)
						this.silentNeighbour = msg.getSender();
					this.recp.put(msg.getSender(), true);
				} else {
					throw new IllegalStateException("Message Sender not found in Recp");
				}
				this.winNode = this.winNode.getId() < msg.getElectionNode().getId() ? this.winNode
						: msg.getElectionNode();
			}
			return true;
		}
		return false;
	}

	private boolean readMessage(MessageType msgType) {
		if (this.messageQueue.size() > 0) {
			Message msg = this.messageQueue.poll();
			switch (msgType) {
			case ELECTION_WAKEUP_MESSAGE: {
				if (msg.getMessagetype() == MessageType.ELECTION_WAKEUP_MESSAGE) {
					this.recivedWakeupCalls += 1;
					return true;
				} else {
					this.messageQueue.add(msg);
					return false;
				}
			}
			case ELECTION_TOKEN_MESSAGE: {
				if (msg.getMessagetype() == MessageType.ELECTION_TOKEN_MESSAGE) {
					if (this.recp.get(msg.getSender()) != null) {
						if (this.getFalseNodeAmountInRecp() == 1)
							this.silentNeighbour = msg.getSender();
						this.recp.put(msg.getSender(), true);
					} else {
						throw new IllegalStateException("Message Sender not found in Recp");
					}
					this.winNode = this.winNode.getId() < msg.getElectionNode().getId() ? this.winNode
							: msg.getElectionNode();
					return true;
				} else {
					this.messageQueue.add(msg);
					return false;
				}
			}
			}
		}
		return false;
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

	private boolean hasWakeupMessage() {
		return this.messageQueue.stream().filter(msg -> msg.getMessagetype() == MessageType.ELECTION_WAKEUP_MESSAGE)
				.count() > 0;
	}

	private boolean hasTokenMessage() {
		return this.messageQueue.stream().filter(msg -> msg.getMessagetype() == MessageType.ELECTION_TOKEN_MESSAGE)
				.count() > 0;
	}
}
