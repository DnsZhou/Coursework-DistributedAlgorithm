package uk.ac.ncl.TongZhou.DistrAlgori.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class ServerNode{
	private int id;
	private Map<Integer, Boolean> recp;
	private int load;
	private ServerNode parent;
	private List<ServerNode> children;
	private List<Message> messages;

	public ServerNode(int id) {
		this.id = id;
		this.children = new ArrayList<ServerNode>();
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

	public Map<Integer, Boolean> getRecp() {
		return recp;
	}

	public boolean addToRecp(Integer serverId, Boolean value) {
		if (this.getRecp().get(serverId) != null) {
			this.getRecp().put(serverId, value);
			return true;
		} else
			return false;
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
	
	public void initialRecp() {

	}

	public int getLoad() {
		return load;
	}
}
