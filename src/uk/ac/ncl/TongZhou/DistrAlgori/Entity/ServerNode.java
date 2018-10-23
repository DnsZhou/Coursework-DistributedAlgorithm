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
	private List<Message> messageQueue;
	private Map<ServerNode, Boolean> recp;
	private ServerNode parent;
	private List<ServerNode> children;

	public ServerNode(int id) {
		this(id, null);
	}

	public ServerNode(int id, ServerNode parent) {
		this.id = id;
		this.parent = parent;
		this.children = new ArrayList<ServerNode>();
		this.recp = new HashMap<>();
		this.messageQueue = new LinkedList<Message>();
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
}
